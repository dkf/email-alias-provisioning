Google Apps email alias provisioning from the command line

Name a script email-aliases:

```bash
#!/bin/bash

java -jar  ~/Dropbox/lib/alias-provisioning.jar -e user@yourdomain.com -d yourdomain.com -u admin@yourdomain.com "$@"
```

then enjoy:

```bash
$ email-aliases
Password:
spam@yourdomain.com
ruwt@yourdomain.com
aws@yourdomain.com
$ email-aliases rm spam@yourdomain.com
Password:
Removed alias 'spam@yourdomain.com' from 'user@yourdomain.com'
$ email-aliases add skype@yourdomain.com
Password:
Added alias 'skype@yourdomain.com' to 'user@yourdomain.com'
$ email-aliases
Password:
ruwt@yourdomain.com
aws@yourdomain.com
skype@yourdomain.com
```

The code in repo is copyright Google, released under Apache 2.
From: http://code.google.com/p/gdata-java-client/
