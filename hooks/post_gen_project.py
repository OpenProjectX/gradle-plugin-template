#!/usr/bin/env python3
"""Post-generation hook for the Gradle plugin template."""

import os
import stat

gradlew_path = "gradlew"
if os.path.exists(gradlew_path):
    st = os.stat(gradlew_path)
    os.chmod(gradlew_path, st.st_mode | stat.S_IEXEC | stat.S_IXGRP | stat.S_IXOTH)

print(f"\nProject '{{ cookiecutter.project_name }}' generated successfully!")
print(f"  Plugin ID    : {{ cookiecutter.plugin_id }}")
print(f"  Package      : {{ cookiecutter.package_name }}")
print(f"  Plugin class : {{ cookiecutter.plugin_class_name }}Plugin")
print(f"\nGet started:")
print(f"  cd {{ cookiecutter.project_slug }}")
print(f"  ./gradlew build")
