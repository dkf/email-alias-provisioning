package net.muckrake.email.aliasprovisioning;

import io.airlift.command.Cli;
import io.airlift.command.Cli.CliBuilder;
import io.airlift.command.Help;

public class Main {

  static Cli<Runnable> getCli() {
    CliBuilder<Runnable> builder = Cli.buildCli("alias-provisioning", Runnable.class)
        .withDescription("easy creation of google apps email aliases from the command line")
        .withDefaultCommand(ListAliases.class)
        .withCommand(Help.class)
        .withCommand(ListAliases.class)
        .withCommand(AddAlias.class)
        .withCommand(RemoveAlias.class);

    return builder.build();
  }

  public static void main(String[] args) {
    getCli().parse(args).run();
  }

}
