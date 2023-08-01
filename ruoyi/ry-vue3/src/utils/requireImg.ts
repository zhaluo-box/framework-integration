export const requirePath = (imgPath:string):string=>{
  try {
    const handlePath:string = imgPath.replace("@",'..')
    return new URL(handlePath,import.meta.url).href
  } catch (error) {
    console.warn(error)
  }
}