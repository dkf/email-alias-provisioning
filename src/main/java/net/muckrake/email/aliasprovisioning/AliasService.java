package net.muckrake.email.aliasprovisioning;

import static com.google.common.base.Throwables.propagate;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.gdata.util.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.gdata.client.appsforyourdomain.AppsPropertyService;
import com.google.gdata.data.appsforyourdomain.AppsForYourDomainException;
import com.google.gdata.data.appsforyourdomain.generic.GenericEntry;
import com.google.gdata.data.appsforyourdomain.generic.GenericFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

class AliasService {

  private String domain;
  private String username;
  private String password;

  private AliasService() {}

  static AliasService aliasService(AliasCommand command) {
    AliasService aliasService = new AliasService();
    aliasService.domain = checkNotNull(command.domain);
    aliasService.username = checkNotNull(command.username);
    aliasService.password = checkNotNull(command.password);
    return aliasService;
  }

  private AppsPropertyService getService() {
    AppsPropertyService service = new AppsPropertyService(domain);
    try {
      service.setUserCredentials(username, password);
    } catch (AuthenticationException e) {
      propagate(e);
    }
    return service;
  }

  private List<GenericEntry> retrieveAllPages(AppsPropertyService appService, URI feedUrl) {
    List<GenericEntry> allEntries = newArrayList();
    try {
      do {
        GenericFeed feed = appService.getFeed(feedUrl.toURL(), GenericFeed.class);
        allEntries.addAll(feed.getEntries());
        feedUrl = (feed.getNextLink() == null) ? null : new URI(feed.getNextLink().getHref());
      } while (feedUrl != null);
    } catch (ServiceException e) {
      throw propagate(e);
    } catch (IOException e) {
      throw propagate(e);
    } catch (URISyntaxException e) {
      throw propagate(e);
    }
    return allEntries;
  }

  public ImmutableList<String> getAliasesForUser(String userEmail) {
    return FluentIterable.from(retrieveAllPages(getService(), getAliasesForUserURI(userEmail)))
        .transform(new Function<GenericEntry, String>() {
          public String apply(GenericEntry input) {
            return input.getProperty("aliasEmail");
          }
        })
        .toImmutableList();
  }

  private URI getAliasesForUserURI(String userEmail) {
    try {
      return new URI("https://apps-apis.google.com/a/feeds/alias/2.0/" + domain + "?userEmail=" + userEmail);
    } catch (URISyntaxException e) {
      throw propagate(e);
    }
  }

  public void addAlias(String userEmail, String newAlias) {
    GenericEntry entry = new GenericEntry();
    entry.addProperty("userEmail", userEmail);
    entry.addProperty("aliasEmail", newAlias);
    try {
      getService().insert(getAddAliasURI().toURL(), entry);
    } catch (AppsForYourDomainException e) {
      throw propagate(e);
    } catch (MalformedURLException e) {
      throw propagate(e);
    } catch (IOException e) {
      throw propagate(e);
    } catch (ServiceException e) {
      throw propagate(e);
    }
  }

  private URI getAddAliasURI() {
    try {
      return new URI("https://apps-apis.google.com/a/feeds/alias/2.0/" + domain);
    } catch (URISyntaxException e) {
      throw propagate(e);
    }
  }

  public void rmAlias(String userEmail, String deleteAlias) {
    try {
      getService().delete(getRmAliasURI(deleteAlias).toURL());
    } catch (AppsForYourDomainException e) {
      throw propagate(e);
    } catch (MalformedURLException e) {
      throw propagate(e);
    } catch (IOException e) {
      throw propagate(e);
    } catch (ServiceException e) {
      throw propagate(e);
    }
  }

  private URI getRmAliasURI(String deleteAlias) {
    try {
      return new URI("https://apps-apis.google.com/a/feeds/alias/2.0/" + domain + "/" + deleteAlias);
    } catch (URISyntaxException e) {
      throw propagate(e);
    }
  }

}
