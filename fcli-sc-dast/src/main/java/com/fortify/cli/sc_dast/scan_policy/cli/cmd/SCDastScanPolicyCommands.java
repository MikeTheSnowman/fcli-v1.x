package com.fortify.cli.sc_dast.scan_policy.cli.cmd;

import com.fortify.cli.common.cli.cmd.AbstractFortifyCLICommand;
import com.fortify.cli.common.variable.PredefinedVariable;

import picocli.CommandLine.Command;

@Command(
        name = "scan-policy",
        subcommands = {
                SCDastScanPolicyGetCommand.class,
                SCDastScanPolicyListCommand.class
        }
)
@PredefinedVariable(name = "_scdast_currentScanPolicy", field = "id")
public class SCDastScanPolicyCommands extends AbstractFortifyCLICommand {
}
