package net.muckrake.email.aliasprovisioning;

import static net.muckrake.email.aliasprovisioning.AliasService.aliasService;
import io.airlift.command.Arguments;
import io.airlift.command.Command;

import java.util.List;

@Command(name = "rm")
public class RemoveAlias extends AliasCommand {

  @Arguments(description = "aliases to remove")
  public List<String> deleteAliases;

  public void run() {
    AliasService service = aliasService(this);
    for (String deleteAlias : deleteAliases) {
      service.rmAlias(userEmail, deleteAlias);
      print("Removed alias '%s' from '%s'", deleteAlias, userEmail);
    }
  }

}
