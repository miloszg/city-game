package com.example.citygame.api.client;

import com.example.citygame.Models.GameRequest;
import com.example.citygame.Models.GameResponse;
import com.example.citygame.Models.ScenarioRequest;
import com.example.citygame.Models.ScenarioResponse;
import com.example.citygame.Models.User;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiClient {

    private static final String BASE_URL = "http://34.91.192.193:3000/api/v1";
    private static final MediaType JSON = MediaType.get("application/json");
    private Gson serializer = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();
    private OkHttpClient client = new OkHttpClient();

    public void registerNewUser(String userName, String email, String password) throws EmailTakenException, UserRegistrationFailedException, IOException {
        String serializedBody = this.serializer.toJson(new UserRegistrationRequestDTO(userName, email, password));
        RequestBody body = RequestBody.create(serializedBody, JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + "/registration")
                .post(body)
                .build();

        try (Response response = this.client.newCall(request).execute()) {

            switch (response.code()) {
                case 201:
                case 202:
                case 204:
                    return;
                case 400:
                    throw new EmailTakenException();

                case 422:
                    throw new UserRegistrationFailedException();

                default:
                    throw new GenericApiException();
            }
        } catch (IOException e) {
           throw e;
        }
    }

    public UserLoginResponseDTO login(String email, String password) throws InvalidCredentialsException, IOException {
        HttpUrl url = HttpUrl.parse(BASE_URL + "/session").newBuilder()
                .addEncodedQueryParameter("email", email)
                .addEncodedQueryParameter("password", password)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Device-Id", "mikolaj")
                .get()
                .build();

        try (Response response = this.client.newCall(request).execute()) {

            switch (response.code()) {
                case 200:
                case 202:
                    return this.serializer.fromJson(response.body().string(), UserLoginResponseDTO.class);
                case 400:
                    throw new InvalidCredentialsException();
                default:
                    throw new GenericApiException();
            }
        } catch (IOException e) {
            throw e;
        } catch (NullPointerException e) {
            throw new GenericApiException();
        }
    }

    public String createScenario(ScenarioRequest scenario) throws IOException {
        Request request;
        try {
            String serializedBody = this.serializer.toJson(scenario);
            RequestBody body = RequestBody.create(serializedBody, JSON);
            request = new Request.Builder()
                    .url(BASE_URL + "/scenarios")
                    .addHeader("Authorization", User.getInstance().getAccessToken())
                    .post(body)
                    .build();
        }catch (Exception e){
            return e.getMessage();
        }

        try (Response response = this.client.newCall(request).execute()) {
            switch (response.code()) {
                case 201:
                case 202:
                case 204:
                case 400:
                default:
            }
        } catch (IOException e) {
            return e.getMessage();
        }

        return "ok";
    }

    public ScenarioResponse[] getAllScenarios(){
        Request request = new Request.Builder()
                .url(BASE_URL + "/scenarios")
                .addHeader("Authorization", User.getInstance().getAccessToken())
                .build();

        try (Response response = this.client.newCall(request).execute()) {
            switch (response.code()) {
                case 201:
                case 200:
                    return this.serializer.fromJson(response.body().string(), ScenarioResponse[].class);
                case 204:
                case 400:
                default:
            }
        } catch (IOException e) {
        }
        return null;
    }

    public GameResponse[] getAllGames(){
        Request request = new Request.Builder()
                .url(BASE_URL + "/games")
                .addHeader("Authorization", User.getInstance().getAccessToken())
                .build();

        try (Response response = this.client.newCall(request).execute()) {
            switch (response.code()) {
                case 201:
                case 200:
                    return this.serializer.fromJson(response.body().string(), GameResponse[].class);
                case 204:
                case 400:
                default:
            }
        } catch (IOException e) {
        }
        return null;
    }

    public void createNewGame(GameRequest game){
        Request request = null;
        try {
            String serializedBody = this.serializer.toJson(game);
            RequestBody body = RequestBody.create(serializedBody, JSON);
            request = new Request.Builder()
                    .url(BASE_URL + "/games")
                    .addHeader("Authorization", User.getInstance().getAccessToken())
                    .post(body)
                    .build();
        }catch (Exception e){
        }

        try (Response response = this.client.newCall(request).execute()) {
            switch (response.code()) {
                case 201:
                case 202:
                case 204:
                case 400:
                default:
            }
        } catch (IOException e) {
        }

    }

    public ScenarioResponse getScenario(String id){
        Request request = new Request.Builder()
                .url(BASE_URL + "/scenarios/"+id)
                .addHeader("Authorization", User.getInstance().getAccessToken())
                .build();

        try (Response response = this.client.newCall(request).execute()) {
            switch (response.code()) {
                case 201:
                case 200:
                    return this.serializer.fromJson(response.body().string(), ScenarioResponse.class);
                case 204:
                case 400:
                default:
            }
        } catch (IOException e) {
        }
        return null;
    }

    public void passwordChangeRequest(String email) throws EmailNotRecognizedException, IOException {
        String serializedBody = this.serializer.toJson(new UserPasswordChangeRequestDTO(email));
        RequestBody body = RequestBody.create(serializedBody, JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + "/password_reset")
                .post(body)
                .build();

        try (Response response = this.client.newCall(request).execute()) {

            switch (response.code()) {
                case 201:
                case 202:
                case 204:
                    return;
                case 400:
                    throw new EmailNotRecognizedException();
                default:
                    throw new GenericApiException();
            }
        } catch (IOException e) {
            throw e;
        }
    }

    public UserPasswordChangeTokenResponseDTO passwordChangeTokenResponse(String token, String password) throws InvalidTokenException, IOException {
        HttpUrl url = HttpUrl.parse(BASE_URL + "/password_reset").newBuilder()
                .addEncodedQueryParameter("token", token)
                .addEncodedQueryParameter("password", password)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = this.client.newCall(request).execute()) {

            switch (response.code()) {
                case 200:
                case 202:
                case 204:
                    return this.serializer.fromJson(response.body().string(), UserPasswordChangeTokenResponseDTO.class);
                case 400:
                    throw new InvalidTokenException();
                default:
                    throw new GenericApiException();
            }
        } catch (IOException e) {
            throw e;
        } catch (NullPointerException e) {
            throw new GenericApiException();
        }
    }
}