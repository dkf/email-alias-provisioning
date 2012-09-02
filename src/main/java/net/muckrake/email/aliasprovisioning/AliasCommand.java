package net.muckrake.email.aliasprovisioning;

import static io.airlift.command.OptionType.GLOBAL;
import static java.lang.String.format;
import io.airlift.command.Option;

import java.io.PrintStream;

public abstract class AliasCommand implements Runnable {

  @Option(required = true, name = "-e", description = "Email to list aliases for", type = GLOBAL)
  public String userEmail;

  @Option(required = true, name = "-d", description = "Domain to list aliases for", type = GLOBAL)
  public String domain;

  @Option(required = true, name = "-u", description = "Admin username", type = GLOBAL)
  public String username;

  @Option(required = true, name = "-p", description = "Admin password", type = GLOBAL)
  public String password;

  protected PrintStream out = System.out;

  void print(String fmt, Object...args) {
    out.println(format(fmt, args));
  }
}
