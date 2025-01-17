package com.fortify.cli.app.i18n;

import java.util.Locale;

import com.fortify.cli.common.cli.util.IFortifyCLIInitializer;
import com.fortify.cli.common.cli.util.FortifyCLIInitializerRunner.FortifyCLIInitializerCommand;
import com.fortify.cli.config.language.util.LanguagePropertiesManager;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class LanguageInitializer implements IFortifyCLIInitializer {
    private final LanguagePropertiesManager config;

    @Inject
    public LanguageInitializer(LanguagePropertiesManager config) {
        this.config = config;
    }

    @Override
    public void initializeFortifyCLI(FortifyCLIInitializerCommand cmd) {
        Locale.setDefault(config.getLocale());
    }
}