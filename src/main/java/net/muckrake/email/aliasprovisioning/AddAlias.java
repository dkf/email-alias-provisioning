package net.muckrake.email.aliasprovisioning;

import static net.muckrake.email.aliasprovisioning.AliasService.aliasService;
import io.airlift.command.Arguments;
import io.airlift.command.Command;

import java.util.List;

@Command(name = "add")
public class AddAlias extends AliasCommand {

  @Arguments(description = "aliases to add")
  public List<String> newAliases;

  @Override
  public void execute() {
    AliasService service = aliasService(this);
    for (String newAlias : newAliases) {
      service.addAlias(userEmail, newAlias);
      print("Added alias '%s' to '%s'", newAlias, userEmail);
    }
  }

}
