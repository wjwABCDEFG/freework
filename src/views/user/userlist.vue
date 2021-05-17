<template>
  <div>
    <el-tree
      :data="data"
      node-key="id"
      default-expand-all
      @node-drag-end="handleDragEnd"
      @node-drop="handleDrop"
      draggable
      :allow-drop="allowDrop"
      allow-drag="true"
    ></el-tree>
  </div>
</template>

<script>
export default {
  data() {
    return {
      data: [
        {
          id: 1,
          label: "一级 1",
        },
        {
          id: 2,
          label: "一级 2",
        },
        {
          id: 3,
          label: "一级 3",
        },
      ],
      defaultProps: {
        children: "children",
        label: "label",
      },
    };
  },
  methods: {
    // handleDragStart(node, ev) {
    //   console.log("drag start", node);
    // },
    // handleDragEnter(draggingNode, dropNode, ev) {
    //   console.log("tree drag enter: ", dropNode.label);
    // },
    // handleDragLeave(draggingNode, dropNode, ev) {
    //   console.log("tree drag leave: ", dropNode.label);
    // },
    // handleDragOver(draggingNode, dropNode, ev) {
    //   console.log("tree drag over: ", dropNode.label);
    // },
    handleDragEnd(draggingNode, dropNode, dropType, ev) {
      console.log("tree drag end: ", dropNode && dropNode.label, dropType);
    },
    handleDrop(draggingNode, dropNode, dropType, ev) {
      console.log("tree drop: ", dropNode.label, dropType);
    },
    allowDrop(draggingNode, dropNode, type) {
      return true;
    },
  },
};
</script>

