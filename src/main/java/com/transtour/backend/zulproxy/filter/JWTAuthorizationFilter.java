package com.transtour.backend.zulproxy.filter;

import com.google.gson.Gson;
import com.sun.org.apache.xerces.internal.impl.xs.util.ShortListImpl;
import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private static  String HEADER ="Authorization";
    private  static String PREFIX ="Bearer ";
    private static String SECRET_KEY = "test";


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if(existJWTToken(request,response)){
                Claims claims = validateToken(request);
                if (claims.get("authorities") !=null){
                    setUpSpringAuhtentication(claims);
                }else{
                    SecurityContextHolder.clearContext();
                }
            }else{
                SecurityContextHolder.clearContext();
            }
            filterChain.doFilter(request,response);
        }catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            Map result = new HashMap();
            result.put("status", HttpServletResponse.SC_FORBIDDEN);
            result.put("message","Unhautorized -token expired");
            Gson gson = new Gson();
            String json = gson.toJson(result);
            response.getWriter().write(json);
            response.getWriter().flush();
            return;
        }
    }

    private void setUpSpringAuhtentication(Claims claims) {
        String autority = (String) claims.get("authorities");

        UsernamePasswordAuthenticationToken auth = new
                UsernamePasswordAuthenticationToken(
                     claims.getSubject(),
                     null,
                Collections.singleton(new SimpleGrantedAuthority(autority)));
        SecurityContextHolder.getContext().setAuthentication(auth);


    }

    private Claims validateToken(HttpServletRequest request) {
        String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(jwtToken).getBody();
    }

    private boolean existJWTToken(HttpServletRequest request, HttpServletResponse response){
        String auhtenticationHeader = request.getHeader(HEADER);
        if (auhtenticationHeader == null || !auhtenticationHeader.startsWith(PREFIX)) return false;
        else  return  true;
    }
}
