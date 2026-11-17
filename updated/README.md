# Updated browser example

Added 17 November 2026 as a new, project-specific interaction. It is separate from the recovered software and is not the students' implementation.

## What runs

Choose fictional posters, toggle their diagrams and annotations, adjust placement and export a selection.

All three posters, diagrams and author labels are new fictional examples. The recovered source contains content selection/download classes but lacks the tracking integration, SDK and required resources. The demo illustrates how posters link to content; it is not a working AR build.

## Run

Open https://twerdster.github.io/augmented-reality-posters/ or serve the repository's `docs/` directory with any static HTTP server. For example, from the repository root:

```sh
python3 -m http.server 8000 --directory docs
```

Open `http://localhost:8000`. The demo uses standard Canvas 2D and local browser controls. No additional demo packages, SDKs or credentials are required. Downloads are generated JSON files. No camera permission is requested.

## Implementation

`docs/index.html` contains the controls and factual scope labels; `docs/demo.js` implements the interaction; `docs/demo.css` contains the responsive layout. All generated images/data are illustrative. The demo uses local records and manual placement; it does not recognize posters or run QCAR. Proprietary QCAR sample and SDK files are not included.

## Recorded checks

[VALIDATION.json](VALIDATION.json) records 10 passed browser assertions on the new interaction, JSON export where present, local-only requests, error-free JavaScript and a 390-pixel responsive layout. These are interaction assertions on generated/local examples, not accuracy or compatibility benchmarks. Original native application/hardware coverage is unchanged; see [historical recovery notes](../docs/RECOVERY.md).

## Attribution and terms

Original project: Michael Weiss, Myr Hagay. Supervision: Aaron Wetzler. The new browser example was prepared with Codex assistance in November 2026; Claude reviewed the README wording and factual scope. The new files have a [scoped MIT license](../docs/DEMO-LICENSE.txt); it does not relicense historical code, images, slides, reports or third-party dependencies.
