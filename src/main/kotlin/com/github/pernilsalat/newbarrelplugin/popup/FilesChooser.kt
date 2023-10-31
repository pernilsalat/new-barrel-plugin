package com.github.pernilsalat.newbarrelplugin.popup
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.psi.PsiFileSystemItem
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.treeStructure.Tree
import javax.swing.JComponent
import javax.swing.tree.*

class FilesChooser : DialogWrapper(true) {
    private var tree: Tree = Tree();

    init {
        super.init();
        super.setTitle("Select Files to Export");
    }

    override fun createCenterPanel(): JComponent {
        val root = DefaultMutableTreeNode("root");
        root.add(DefaultMutableTreeNode("caca"))
        root.add(DefaultMutableTreeNode("vaca"))
        root.add(DefaultMutableTreeNode("pernil"))
        val treeModel = DefaultTreeModel(root);
        val newTree = Tree(treeModel);
        newTree.selectionModel.selectionMode = TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION;
        tree = newTree;

        return panel {
            row {
                cell(tree)
            }
        }
    }

    fun getSelectedNodes(): List<String> {
        return tree
            .getSelectedNodes(DefaultMutableTreeNode::class.java, null)
            .map { it.toString() }
    }
}