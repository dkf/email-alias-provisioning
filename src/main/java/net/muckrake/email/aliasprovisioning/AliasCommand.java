package net.muckrake.email.aliasprovisioning;

import static io.airlift.command.OptionType.GLOBAL;
import static java.lang.String.format;
import io.airlift.command.Option;

import java.io.Console;
import java.io.PrintStream;

public abstract class AliasCommand implements Runnable {

  @Option(required = true, name = "-e", description = "Email to list aliases for", type = GLOBAL)
  public String userEmail;

  @Option(required = true, name = "-d", description = "Domain to list aliases for", type = GLOBAL)
  public String domain;

  @Option(required = true, name = "-u", description = "Admin username", type = GLOBAL)
  public String username;

  @Option(name = "-p", description = "Admin password, will prompt if absent", type = GLOBAL)
  public String password;

  protected PrintStream out = System.out;

  void print(String fmt, Object...args) {
    out.println(format(fmt, args));
  }
  
  @Override
  public final void run() {
    if (password == null || password.isEmpty()) {
      Console cons;
      char[] passwd;
      if ((cons = System.console()) != null &&
          (passwd = cons.readPassword("%s ", "Password:")) != null) {
        password = new String(passwd);
      } else {
        throw new RuntimeException("Unable to prompt for password at the console");
      }
    }
    execute();
  }
  
  abstract void execute();
  
}
