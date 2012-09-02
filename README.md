Google Apps email alias provisioning from the command line

Name a script email-aliases:

```bash
#!/bin/bash

java -jar  ~/Dropbox/lib/alias-provisioning.jar -e user@yourdomain.com -d yourdomain.com -u admin@yourdomain.com "$@"
```

then enjoy:

```bash
$ email-aliases -p password
spam@yourdomain.com
ruwt@yourdomain.com
aws@yourdomain.com
$ email-aliases -p password rm spam@yourdomain.com
Removed alias 'spam@yourdomain.com' from 'user@yourdomain.com'
$ email-aliases -p password add skype@yourdomain.com
Added alias 'skype@yourdomain.com' to 'user@yourdomain.com'
$ email-aliases -p password
ruwt@yourdomain.com
aws@yourdomain.com
skype@yourdomain.com
```

The code in repo is copyright Google, released under Apache 2.
From: http://code.google.com/p/gdata-java-client/
