// 使用 import.meta.glob 来动态导入所有 SVG 文件
const svgModules = import.meta.glob('./svg/*.svg');

const requireAll = async (modules) => {
  const imports = [];
  for (const path in modules) {
    imports.push(modules[path]());
  }
  return await Promise.all(imports);
};

requireAll(svgModules).then((svgs) => {
  // console.log(svgs); // 这里你可以得到所有的 SVG 文件的内容
});
