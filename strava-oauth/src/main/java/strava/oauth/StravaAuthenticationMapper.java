package strava.oauth;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.oauth2.endpoint.authorization.state.State;
import io.micronaut.security.oauth2.endpoint.token.response.OauthAuthenticationMapper;
import io.micronaut.security.oauth2.endpoint.token.response.TokenResponse;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Named("strava")
@Singleton
public class StravaAuthenticationMapper implements OauthAuthenticationMapper {

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String ROLE_STRAVA = "ROLE_STRAVA";

    private final StravaApiClient apiClient;

    public StravaAuthenticationMapper(StravaApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    public Publisher<AuthenticationResponse> createAuthenticationResponse(TokenResponse tokenResponse,
                                                                          @Nullable State state) {
        return Mono.from(apiClient.getUser(TOKEN_PREFIX + tokenResponse.getAccessToken()))
                .map(user -> AuthenticationResponse.success(user.getUsername(),
                        Collections.singletonList(ROLE_STRAVA),
                        Collections.singletonMap(ACCESS_TOKEN_KEY, tokenResponse.getAccessToken())));
    }
}