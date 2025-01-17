package com.fortify.cli.ssc.appversion_user.cli.mixin;

import io.micronaut.core.annotation.ReflectiveAccess;
import lombok.Getter;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

public class SSCAppVersionAuthEntityMixin {
    @ReflectiveAccess
    public static abstract class AbstractSSCAppVersionAuthEntityMixin {
        public abstract String[] getAuthEntitySpecs();
    }
    
    @ReflectiveAccess
    public static class OptionalUserAddOption extends AbstractSSCAppVersionAuthEntityMixin {
        @Option(names = {"--useradd"}, required = false, arity="0..")
        @Getter private String[] authEntitySpecs;
    }
    
    @ReflectiveAccess
    public static class OptionalUserDelOption extends AbstractSSCAppVersionAuthEntityMixin {
        @Option(names = {"--userdel"}, required = false, arity="0..")
        @Getter private String[] authEntitySpecs;
    }
    
    @ReflectiveAccess
    public static class RequiredPositionalParameter extends AbstractSSCAppVersionAuthEntityMixin {
        @Parameters(index = "0..*", arity = "1..*")
        @Getter private String[] authEntitySpecs;
    }
    
    
}
