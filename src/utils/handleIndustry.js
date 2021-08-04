export function handleIndustryData(src) {
  let dest = []
  let pids = Object.keys(src.level0)
  let labels = Object.values(src.level0)
  dest = labels.map((item, index) => {
    return {
      value: item,
      label: item,
    }
  })

  let childrens = []
  for (let i = 0; i < dest.length; i++) {
    let tempChild = src.level1.filter((item) => {
      return item.pid == i
    })
    let child = tempChild.map((item) => {
      return {
        label: item.title,
        value: item.title
      }
    })
    childrens.push(child)
  }

  let newData = dest.map((item, index) => {
    return {
      ...item,
      children: childrens[index]
    };
  })

  return newData
}