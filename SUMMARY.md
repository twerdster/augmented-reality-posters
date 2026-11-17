# augmented-reality-posters: maintenance context

Read this file, then SKILL.md, README.md and linked recovery/provenance records. Current update: 17 November 2026.

The Android project associated printed posters with downloadable digital content for a QCAR-based augmented-reality view.

## Original material

Ten unchanged Java, JNI-header and configuration files preserve poster selection and content-download code. Missing SDK integration and resources prevent a standalone build. Original source, submodule pins, images, reports, slides, notices and author-response records are unchanged. [Historical implementation and recovery notes](docs/RECOVERY.md) retain the earlier setup and failed-build evidence; existing PROVENANCE.md or source/PROVENANCE.json remains authoritative where present.

## Updated demo

Choose fictional posters, toggle their diagrams and annotations, adjust placement and export a selection. This is new independent JavaScript/Canvas code in docs/, not a restored or ported historical application. The demo uses local records and manual placement; it does not recognize posters or run QCAR. Excluded proprietary sample/SDK material remains excluded.

Run https://twerdster.github.io/augmented-reality-posters/ or `python3 -m http.server 8000 --directory docs` from the repository root. No build, account, SDK or external service is required. Serve docs/ as the GitHub Pages source.

## Checks and review

updated/VALIDATION.json records 10 passing Chromium browser assertions for the new demo, including responsive layout checks; these are scoped interaction checks on synthetic/local examples. No original native build, sensor accuracy or physical-mobile/Safari/Firefox coverage was added. Two Claude CLI editorial passes reviewed the concise README and new-demo guide; useful wording and scope qualifications were checked against code and recovery records. Detailed review records are private.

Preserve attribution, original bytes and licensing boundaries. New demo code has a scoped MIT license in docs/DEMO-LICENSE.txt; it does not cover historical materials or third-party dependencies. Correspondence, personal contact data and machine-specific setup details stay private. Before every commit review and synchronize this file and SKILL.md.
