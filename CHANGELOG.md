# Change Log
All notable changes to this project will be documented in this file.

## [Unreleased]

## [v3.1.1]

## Changed
- chore: bump logback@1.5.8
- chore: bump assetj-swing-junit@4.0.0-beta-2
- style: remove redundant public modifier from interface methods
- style: use individual imports instead of '*' imports

## [v3.1.0]

## Added
* Respect `UIDefauls` table configuration defined in `LookAndFeel` class.(#11)
* Allow a customization by inheriting `DockingUISettings` class and overide `getDefaults` method.(#11)

### Fixed
* Avoid NPE when drag-n-drop with null hints (#9)

### Changed
* Add GitHub Actions CI
* Add SpotBugs and SpotLess code quality assurance
* Style: spotless code format applied (#8)
* feat: test UI behavior with Assertj-Swing-JUnit (#7)
* refactor: annotate deprecations, avoid access to internal class of other class(#6)
* refactor: avoid redundant casts (#6)
* refactor: DockingDesktop class not to initialize in static context(#12)

## [v3.0.5-2]
* chore: use nexus-publish plugin to release

## [v3.0.5-1]

### Fixed
* Fixed issue with restoring split weight from upstream
* Fix for when awt.font.desktophints is null
* Use Apple-recommended method of detecting OS X

### Added
* Allow customizing the color of AutoHideButtons instead of making them transparent
* Allow disabling custom painting of DockViewTitleBar
* Allow setting background of AutoHideButtonPanel
* Allow setting border on main desktop area
* Use all default rendering hints set by platform when rendering pane tabs
* Original wiki files from https://code.google.com/archive/p/vldocking/
* Add a license file

### Changed
* Require Java 11 Runtime
* use Gradle for build
* Fix: LightWeightPeer deprecated in Java 11
* Bump versions
    - gradle@8.5
        - use version catalog
        - make build.gradle modern
        - remove jfrog related
        - Java version compatible with 11
    - junit@4.13.2
    - logback@1.4.12
    - slf4j@2.0.7
* Delete old JAR, docs ZIP, and maven config

## [v3.0.5]
* Fork original project for OmegaT-org
 
## v3.0.4
* Carsten Madsen released v3.0.4

## v3.0.3
* Carsten Madsen released v3.0.3

[Unreleased]: https://github.com/omegat-org/vldocking/compare/v3.1.1...HEAD
[v3.1.1]: https://github.com/omegat-org/vldocking/compare/v3.1.0...v3.1.1
[v3.1.0]: https://github.com/omegat-org/vldocking/compare/v3.0.5-2...v3.1.0
[v3.0.5-2]: https://github.com/omegat-org/vldocking/compare/v3.0.5-1...v3.0.5-2
[v3.0.5-1]: https://github.com/omegat-org/vldocking/compare/vldocking-3.0.5...v3.0.5-1
[v3.0.5]: https://github.com/omegat-org/vldocking/compare/vldocking-3.0.4...vldocking-3.0.5
