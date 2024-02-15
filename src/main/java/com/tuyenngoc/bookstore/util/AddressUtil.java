package com.tuyenngoc.bookstore.util;

import com.tuyenngoc.bookstore.domain.dto.request.CoordinatesRequestDto;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class AddressUtil {

    public static String getLocationName(CoordinatesRequestDto addressDto) {
        String url = String.format("https://nominatim.openstreetmap.org/reverse?format=json&lat=%s&lon=%s&zoom=18&addressdetails=1",
                addressDto.getLatitude(), addressDto.getLongitude());

        HttpURLConnection connection;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                String json = response.toString();
                int index = json.indexOf("\"display_name\":\"");
                if (index != -1) {
                    int startIndex = index + 16;
                    int endIndex = json.indexOf("\"", startIndex);
                    return json.substring(startIndex, endIndex);
                }
            }
        } catch (Exception e) {
            log.error("Error get address name: {}", e.getMessage(), e);
        }
        return null;
    }

    public static double calculateDistance(CoordinatesRequestDto address1Dto, CoordinatesRequestDto address2Dto) {
        double earthRadius = 6371;

        double latDiff = Math.toRadians(address2Dto.getLatitude() - address1Dto.getLatitude());
        double lonDiff = Math.toRadians(address2Dto.getLongitude() - address1Dto.getLongitude());

        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2)
                + Math.cos(Math.toRadians(address1Dto.getLatitude())) * Math.cos(Math.toRadians(address2Dto.getLatitude()))
                * Math.sin(lonDiff / 2) * Math.sin(lonDiff / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return earthRadius * c;
    }

}