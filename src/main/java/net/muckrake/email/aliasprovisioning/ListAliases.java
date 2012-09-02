package net.muckrake.email.aliasprovisioning;

import static net.muckrake.email.aliasprovisioning.AliasService.aliasService;
import io.airlift.command.Command;

@Command(name = "list")
public class ListAliases extends AliasCommand {

  @Override
  public void execute() {
    AliasService service = aliasService(this);
    for (String alias : service.getAliasesForUser(userEmail)) {
      print(alias);
    }
  }

}
