# Hot of Hello Minecraft! Launcher

English | [中文](README_cn.md)

## Introduction
HotHMCL is a modified version of the cross-platform launcher based on Hello Minecraft! Launcher. It HotHMCL a launcher for Minecraft server clients, supports all functions of HMCL, develops some new functions, and additionally restricts some functions of HMCL launcher.

If you want to know about HMCL launcher, you can click [here](https://github.com/HMCL-dev/HMCL) to visit the open source repository of HMCL launcher.
## Download
You can click on the Release on the right to view the latest release download. We provide a detailed configuration tutorial. You can click [here](https://www.yuque.com/go/book/47982025) to view it.

## License
This procedure follows the international GPLv3 open source license, the specific content please read [LICENSE](https://www.gnu.org/licenses/gpl-3.0.html) file. The following additional terms are attached to the license.
### Additional terms under GPLv3 Section 7
1. When you distribute a modified version of the software, you must change the software name or the version number in a reasonable way in order to distinguish it from the original version. (Under [GPLv3, 7(c)](https://github.com/huanghongxun/HMCL/blob/11820e31a85d8989e41d97476712b07e7094b190/LICENSE#L372-L374))

   The software name and the version number can be edited [here](https://github.com/huanghongxun/HMCL/blob/javafx/HMCL/src/main/java/org/jackhuang/hmcl/Metadata.java#L33-L35).

2. You must not remove the copyright declaration displayed in the software. (Under [GPLv3, 7(b)](https://github.com/huanghongxun/HMCL/blob/11820e31a85d8989e41d97476712b07e7094b190/LICENSE#L368-L370))

## Contribution
If you want to submit a pull request, there are some requirements:
* IDE: Intellij IDEA
* Compiler: Java 11
* Do NOT modify `gradle` files

### Compilation
Simply execute the following command in project root directory:

```bash
./gradlew clean build
```

Make sure you have Java installed with JavaFX 8 at least. Liberica Full JDK 11 or later is recommended.

## JVM Options (for debugging)
| Parameter                                    | Description                                                                                                                       |
|----------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------|
| `-Dhmcl.home=<path>`                         | Override HMCL directory.                                                                                                          |
| `-Dhmcl.self_integrity_check.disable=true`   | Bypass the self integrity check when checking for update.                                                                         |
| `-Dhmcl.bmclapi.override=<version>`          | Override API Root of BMCLAPI download provider, defaults to `https://bmclapi2.bangbang93.com`. e.g. `https://download.mcbbs.net`. |
| `-Dhmcl.font.override=<font family>`         | Override font family.                                                                                                             |
| `-Dhmcl.version.override=<version>`          | Override the version number.                                                                                                      |
| `-Dhmcl.update_source.override=<url>`        | Override the update source.                                                                                                       |
| `-Dhmcl.authlibinjector.location=<path>`     | Use specified authlib-injector (instead of downloading one).                                                                      |
| `-Dhmcl.openjfx.repo=<maven repository url>` | Add custom Maven repository for download OpenJFX.                                                                                 |
| `-Dhmcl.native.encoding=<encoding>`          | Override the native encoding.                                                                                                     |
| `-Dhmcl.microsoft.auth.id=<App ID>`          | Override Microsoft OAuth App ID.                                                                                                  |
| `-Dhmcl.microsoft.auth.secret=<App Secret>`  | Override Microsoft OAuth App secret.                                                                                              |
