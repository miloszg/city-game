package com.example.citygame.api.client;

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

    public UserLoginResponseDTO login(String email, String password) throws InvalidCredentialsException, IOException{
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
}