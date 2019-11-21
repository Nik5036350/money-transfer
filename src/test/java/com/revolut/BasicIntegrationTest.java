package com.revolut;

import com.revolut.helpers.DAOFactory;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(DropwizardExtensionsSupport.class)
public class BasicIntegrationTest {

    protected final static String BASE_URL = "http://localhost:%d";
    private static final String CONFIG_PATH = ResourceHelpers.resourceFilePath("config.yml");

    protected static final DropwizardAppExtension<HwConfiguration> RULE = new DropwizardAppExtension<>(
            HwApplication.class, CONFIG_PATH);

    @BeforeEach
    public void resetDB() {
        new DAOFactory().populateTestData();
    }
}
