package com.matheuscordeiro.springbootasyncgithub.components;

import com.matheuscordeiro.springbootasyncgithub.models.User;
import com.matheuscordeiro.springbootasyncgithub.services.GitHubLookupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

import java.util.concurrent.CompletableFuture;

public class AppRunner implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(GitHubLookupService.class);
    private final GitHubLookupService gitHubLookupService;

    public AppRunner(GitHubLookupService gitHubLookupService) {
        this.gitHubLookupService = gitHubLookupService;
    }

    @Override
    public void run(String... args) throws Exception {
        long start = System.currentTimeMillis();

        CompletableFuture<User> firstPage = gitHubLookupService.findUser("PivotalSoftware");
        CompletableFuture<User> secondPage = gitHubLookupService.findUser("CloudFoundry");
        CompletableFuture<User> thirdPage = gitHubLookupService.findUser("Spring-Projects");

        CompletableFuture.allOf(firstPage, secondPage, thirdPage).join();

        LOGGER.info("Elapsed time: " + (System.currentTimeMillis() - start));
        LOGGER.info("--> " + firstPage.get());
        LOGGER.info("--> " + secondPage.get());
        LOGGER.info("--> " + thirdPage.get());
    }
}
