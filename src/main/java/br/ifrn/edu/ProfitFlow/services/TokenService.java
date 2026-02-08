package br.ifrn.edu.ProfitFlow.services;

import br.ifrn.edu.ProfitFlow.dto.AuthDataDTO;
import br.ifrn.edu.ProfitFlow.dto.DadosTokenJWT;
import br.ifrn.edu.ProfitFlow.models.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;

    @Value("${api.security.token.secret}")
    private String secret;

    @Value("${api.security.token.issuer}")
    private String issuer;

    @Value("${api.security.token.expire-access-token}")
    private Long expireAccessToken;

    @Value("${api.security.token.expire-refresh-token}")
    private Long expireRefreshToken;


    public DadosTokenJWT generateDataToken(AuthDataDTO data) {
        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(data.username(), data.password());
            var authentication = authenticationManager.authenticate(authenticationToken);
            var tokenJWT = generateToken((Usuario) authentication.getPrincipal());
            var refreshTokenJWT = generateRefreshToken((Usuario) authentication.getPrincipal());
            return new DadosTokenJWT(
                    tokenJWT,
                    expireAccessToken,
                    refreshTokenJWT,
                    expireRefreshToken,
                    "Bearer"
                    );
        }catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }


    public String generateToken(Usuario usuario) {
        Algorithm algoritmo = Algorithm.HMAC256(secret);
        return JWT.create()
                .withIssuer(issuer)
                .withSubject(usuario.getEmail())
                .withExpiresAt(dataExpiracao(expireAccessToken))
                .sign(algoritmo);
    }

    public String generateRefreshToken(Usuario usuario) {
        Algorithm algoritmo = Algorithm.HMAC256(secret);
        return JWT.create()
                .withIssuer(issuer)
                .withSubject(usuario.getEmail())
                .withClaim(usuario.getRole().toString(), true)
                .withExpiresAt(dataExpiracao(expireRefreshToken))
                .withClaim("type", "refresh")
                .sign(algoritmo);
    }

    public String getSubject(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer(issuer)
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }

    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            return "";
        }
    }

    private Instant dataExpiracao(Long minutos) {
        return LocalDateTime.now()
                .plusMinutes(minutos)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}
