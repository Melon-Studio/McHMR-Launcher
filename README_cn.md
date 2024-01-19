# Hot of Hello Minecraft! Launcher 

[English](README.md) | 中文

## 简介
HotHMCL 是基于 Hello Minecraft! Launcher 跨平台启动器的修改版本, 支持 HMCL 的全部功能，开发了一些新功能，并额外限制了一些功能，适用于服务器客户端。

你想了解 HMCL 启动器，可以点击[此处](https://github.com/HMCL-dev/HMCL)访问 HMCL 启动器的开源仓库。

## 下载
你可以点击右侧的 Release 查看最新发行版下载，我们提供了详细的配置教程，你可以点击[此处](https://www.yuque.com/go/book/47982025)前往查看。


## 开源协议
本程序遵循国际 GPLv3 开源许可证，具体内容请详细阅读 [LICENSE](https://www.gnu.org/licenses/gpl-3.0.html) 文件。在遵循许可证的同时附有以下额外条款。

### 附加条款 (依据 GPLv3 开源协议第七条)
1. 当您分发该程序的修改版本时, 您必须以一种合理的方式修改该程序的名称或版本号, 以示其与原始版本不同. (依据 [GPLv3, 7(c)](https://github.com/huanghongxun/HMCL/blob/11820e31a85d8989e41d97476712b07e7094b190/LICENSE#L372-L374))

   该程序的名称及版本号可在[此处](https://github.com/huanghongxun/HMCL/blob/javafx/HMCL/src/main/java/org/jackhuang/hmcl/Metadata.java#L33-L35)修改.

2. 您不得移除该程序所显示的版权声明. (依据 [GPLv3, 7(b)](https://github.com/huanghongxun/HMCL/blob/11820e31a85d8989e41d97476712b07e7094b190/LICENSE#L368-L370))

## 贡献
如果您想提交一个 Pull Request, 必须遵守如下要求:
* IDE: Intellij IDEA
* 编译器: Java 11
* **不要**修改 `gradle` 相关文件

### 编译
于项目根目录执行以下命令:

```bash
./gradlew clean build
```

请确保您至少安装了含有 JavaFX 8 的 Java. 建议使用 Liberica Full JDK 11 或更高版本.

## JVM 选项 (用于调试)
| 参数                                           | 简介                                                                                              |
|----------------------------------------------|-------------------------------------------------------------------------------------------------|
| `-Dhmcl.home=<path>`                         | 覆盖 HMCL 数据文件夹.                                                                                  |
| `-Dhmcl.self_integrity_check.disable=true`   | 检查更新时绕过本体完整性检查.                                                                                 |
| `-Dhmcl.bmclapi.override=<version>`          | 覆盖 BMCLAPI 的 API Root, 默认值为 `https://bmclapi2.bangbang93.com`. 例如 `https://download.mcbbs.net`. |
| `-Dhmcl.font.override=<font family>`         | 覆盖字族.                                                                                           |
| `-Dhmcl.version.override=<version>`          | 覆盖版本号.                                                                                          |
| `-Dhmcl.update_source.override=<url>`        | 覆盖更新源.                                                                                          |
| `-Dhmcl.authlibinjector.location=<path>`     | 使用指定的 authlib-injector (而非下载一个).                                                                |
| `-Dhmcl.openjfx.repo=<maven repository url>` | 添加用于下载 OpenJFX 的自定义 Maven 仓库                                                                    |
| `-Dhmcl.native.encoding=<encoding>`          | 覆盖原生编码.                                                                                         |
| `-Dhmcl.microsoft.auth.id=<App ID>`          | 覆盖 Microsoft OAuth App ID.                                                                      |
| `-Dhmcl.microsoft.auth.secret=<App Secret>`  | 覆盖 Microsoft OAuth App 密钥.                                                                      |
