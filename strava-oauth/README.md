# Strava OAuth2 authorization basics
Back when I last touched an IDE, `CVSROOT` was still a commonly used environment variable, the word
cloud was still reserved for meteorologists and Oracle had barely bought Java. Yes, it's been a while.

Recently, I decided to get back into the coding world, and to get back to up to speed and learn all
the new tools and techniques that have been created since, I'm developing all sorts of cycling-related
apps.

One of the things I've been working on is integrating with Strava. This sample shows how to
authorize with Strava - using OAuth2 - from a Micronaut context.

## Usage

### Configuration
This sample features a configuration file: `src/main/resources/application.yml`.
In here, you will need to adjust several things, either directly in the file itself or
using environment variables (this is preferable, and by no means, upload your
client secrete / JWT signature secret to any VCS).

- `JWT_GENERATOR_SIGNATURE_SECRET` - the secret used to sign the JWT tokens. Generate this online, using
  https://www.javainuse.com/jwtgenerator for example.
- `OAUTH_CLIENT_ID` - the Client ID from your Strava API Application (go to Strava > Settings > My API Application to find it)
- `OAUTH_CLIENT_SECRET` - the Client Secret from your Strava API Application

One important to note about the scopes you'll find in the configuration file. These are
the scopes that will be requested. Strava's implementation of OAuth2 doesn't entirely adhere to the
specification in that multiple scopes should be separated by a whitespace, and not a comma. The comma
is what Strava uses. This means multiple scopes cannot be specified using YAMLs normal mechanism of putting
things on multiple lines. Instead, specify all scopes you need on a single line and separate them by a comma.

So, NOT like this:

```
strava
    scopes:
        - activity:read_all
        - profile:write
        - activity:write
```

but like this:

```
strava
    scopes:
        - activity:read_all,profile:write,activity:write
```


Also, make sure you correctly the authorization callback domain as well in your
Strava API Application settings. For development this should be localhost.

Having done this you should be good to go.

### 
The Micronaut app you're looking at has been generated using [Micronaut Launch](http://micronaut.io/launch)
and as such, issuing a `./mvnw mn:run` command should get you going. 
After things have started up, go to http://localhost:8080/ to get start the flow.

## The different elements
The app is composed of various elements and basically mirrors the example
the Micronaut team has created for authenticating with Github. That example
can be seen [here](https://guides.micronaut.io/latest/micronaut-oauth2-github-gradle-java.html).

I won't further explain what's going on here. Instead, please have a look yourself and 
at the Micronaut sample explanation linked to above. This explains all concepts very clearly.

