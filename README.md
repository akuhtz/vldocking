# vldocking

[![Maven Central](https://img.shields.io/maven-central/v/org.bidib.jbidib.com.vldocking/vldocking.svg?label=Maven%20Central&style=flat)](https://search.maven.org/artifact/org.bidib.jbidib.com.vldocking/vldocking)

This is a fork of https://github.com/cmadsen/vldocking
## Why?

Because upstream appears to have been abandoned, but bug fixes and new features were desired for [OmegaT](http://www.omegat.org/).

See https://code.google.com/p/vldocking/ for more information.

## So what's new?

**3.0.9**
* #2: Flash the TitleBar is broken

**3.0.8**
* #1: Add an Automatic-Module-Name to MANIFEST.MF

**3.0.5**
* Made an important fix for Linux (see https://github.com/cmadsen/vldocking/pull/9)
* Additional customization:
  * The background of `AutoHideButton`s is now customizable via the `AutoHideButton.background` key for `UIManager` (`Color` value)
  * The background of `AutoHideButtonPanel` is now customizable via the `AutoHideButtonPanel.background` key for `UIManager` (`Color` value)
  * Disable the gradient on `DockViewTitleBar` via the `DockViewTitleBar.disableCustomPaint` key for `UIManager` (`Boolean` value)
  * The border of the main `DockingDesktop` component can be set via the `DockingDesktop.border` key for `UIManager` (`Border` value)
* Allow customization with key-value in `UIDefaults defaults = UIManager.getDefaults()` table in `LookAndFeel#getDefaults`.
* Released version 3.1.0 with the above changes
* More changes in CHANGELOG.md

Changes introduced by cmadsen/vldocking:

* Renamed package names to use old vlsolutions instead of vldocking
* Fixed issues with read/wrilXML on multiple screens
* Added new empty ctor to `FloatingDialog` to make floating windows appear in taskbar. Extend `DefaultDockableContainerFactory.createFloatingDockableContainer` and return `new FloatingDialog()`
* Fixed LAF issues, e.g., when switching Substance skin
* Fixed issue with borders not being set on single dock windows until an ancestor change events occurred

## How to get the released versions?

### Maven Central

```
<dependency>
	<groupId>org.bidib.jbidib.com.vldocking</groupId>
	<artifactId>vldocking</artifactId>
	<version>3.0.9</version>
</dependency>
```

### Gradle

Just add this to your `build.gradle` file's `dependencies` block:

```
compile 'org.bidib.jbidib.com.vldocking:vldocking:3.0.9'
```

VLDocking, the swing docking framework

The VLDocking framework is a commercial-grade Swing Framework providing rich docking features
which can easily be integrated with existing applications (and of course, new ones).

VLDocking was created in 2004, and is licensed under the LGPL-3.0.

VLDocking is used by companies worldwide and open source projects.

![](https://github.com/akuhtz/vldocking/wiki/vldocking3.jpg)

License and copyright
=====================

VLDocking is licensed under GNU LESSER GENERAL PUBLIC LICENSE version 3.0 or later (as your choice).
VLDocking was initially designed by Lilian Chamontin at his former company VLSolutions. 

Documentation
============

The VLDocking tutorial can be found here https://github.com/akuhtz/vldocking/wiki/tutorial1

## I found a bug; what can I do?

Feel free to fork this repo, and make a "pull request": http://help.github.com/send-pull-requests/.
Ideally, write a test!

## One-time setup
 
Copy the relevant bits from [`settings.xml.example`](./settings.xml.example)
into your own `~/.m2/settings.xml`:

- a `<server id="sonatype-central-portal">` entry with your token as
  username/password, and `njord.publisher` / `njord.releaseUrl` configuration
  so Njord knows this server means "publish to Central Portal"
- (optional) a `<pluginGroup>` entry so you can type `mvn njord:...` instead
  of the fully-qualified plugin coordinates

For the GPG passphrase, the **recommended** approach is to *not* put it in
`settings.xml` at all — export it as an environment variable right before
signing:

```bash
export MAVEN_GPG_PASSPHRASE="your-passphrase"
```

(`settings.xml.example` also shows the older `<server id="gpg.passphrase">`
convention, which still works but is discouraged since 3.2.x of the plugin.)

## Build & test (no signing, no publishing)

```bash
mvn verify
```

The `release` profile (and therefore GPG signing) is *not* active by
default, so this works without a GPG key present.

## Stage, sign, and publish

1. **Locally stage** the release (this creates a Njord "store"; the `id`
   before `::njord:` is a throwaway value just to satisfy deploy-plugin
   syntax):

   ```bash
   mvn -Prelease deploy -DaltDeploymentRepository=id::njord:
   ```

   Because the `release` profile is active, `maven-gpg-plugin` signs the
   jar, sources jar, javadoc jar and POM during the `verify` phase, before
   Njord stages everything locally.

2. **List staged stores** to get the store name:

   ```bash
   mvn njord:list
   ```

3. **Inspect** what got staged (optional but recommended):

   ```bash
   mvn njord:list-content -Dstore=release-xxx
   ```

4. **Validate** the store against Central's requirements (optional but
   recommended — catches missing sources/javadoc/signatures/POM metadata
   before you publish):

   ```bash
   mvn njord:validate -Ddetails -Dstore=release-xxx
   ```

5. **Publish** to Central Portal:

   ```bash
   mvn njord:publish -Dstore=release-xxx -Dpublisher=sonatype-cp
   ```

6. If publish succeeds, the store is dropped automatically and your release
   is now on Central Portal (check https://central.sonatype.com/publishing
   to confirm/finalize, depending on whether your namespace has automatic
   publishing enabled).

If you ever want to abandon a staged release instead of publishing it:

```bash
mvn njord:drop -Dstore=release-xxx
```

## Notes

- Replace the placeholder `groupId`, `url`, `scm`, `developers` and
  `licenses` in `pom.xml` with real values — Central Portal validates that
  this metadata is present and rejects releases that lack it.
- `version.njord` in `pom.xml` should be bumped to whatever the
  [latest Njord release](https://github.com/maveniverse/njord/releases) is
  at the time you use this.
- Signing is scoped to the `release` profile so everyday `mvn test` /
  `mvn install` don't require a GPG key on every dev machine / CI job.
