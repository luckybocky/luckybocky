import { lazy } from "react";

class Util {
  static loadIcon(iconName) {
    return {
      io5: lazy(() =>
        import("react-icons/io5").then((module) => ({
          default: module[iconName],
        }))
      ),
      sl: lazy(() =>
        import("react-icons/sl").then((module) => ({
          default: module[iconName],
        }))
      ),
      ai: lazy(() =>
        import("react-icons/ai").then((module) => ({
          default: module[iconName],
        }))
      ),
      bs: lazy(() =>
        import("react-icons/bs").then((module) => ({
          default: module[iconName],
        }))
      ),
    };
  }
}

export default Util;
