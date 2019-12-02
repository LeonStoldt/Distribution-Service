package de.cloud.fundamentals.distributor.rest;

public interface RequestCallback {

    String getResponseFor(String serviceUrl, String message);

}
