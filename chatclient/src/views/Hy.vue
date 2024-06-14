<template>
    <div>
        <div>
            <el-table border :data="tableData" style="width: 100%">
                <el-table-column align="center" label="头像">
                    <template v-slot="scope">
                        <el-image :src="scope.row.userAvatar" :preview-src-list="[scope.row.userAvatar]"
                            style="width: 50px; height: 50px; border-radius: 50%" />
                    </template>
                </el-table-column>
                <el-table-column align="center" prop="userName" label="姓名"></el-table-column>
                <el-table-column align="center" prop="hyGroupName" label="分组"></el-table-column>
                <el-table-column align="center" label="操作">
                    <template slot-scope="scope">
                        <el-popconfirm title="确定删除吗？" @confirm="del(scope.row.id)">
                            <el-button slot="reference" type="danger" style="margin-left: 5px">删除</el-button>
                        </el-popconfirm>
                        <el-button type="primary" @click="openMoveGroupDialog(scope.row)">移动</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div style="margin-top: 10px">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                :current-page="params.pageNum" :page-sizes="[5, 10, 15, 20]" :page-size="params.pageSize"
                layout="total, sizes, prev, pager, next" :total="total">
            </el-pagination>
        </div>

        <!-- 修改分组对话框 -->
        <el-dialog :visible.sync="moveGroupDialogVisible" title="修改分组">
            <el-form :model="currentFriend">
                <el-form-item label="姓名">
                    <el-input v-model="currentFriend.userName" disabled></el-input>
                </el-form-item>
                <el-form-item label="新分组">
                    <el-input v-model="newGroupName"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="moveGroupDialogVisible = false">取消</el-button>
                <el-button type="primary" @click="updateGroup">确认</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import request from "@/utils/request";

export default {
    data() {
        return {
            params: {
                pageNum: 1,
                pageSize: 5
            },
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
            tableData: [],
            total: 0,
            dialogFormVisible: false,
            form: {},
            moveGroupDialogVisible: false,
            currentFriend: {},
            newGroupName: ""
        }
    },
    created() {
        this.findBySearch();
    },
    methods: {
        findBySearch() {
            request.get("/hy/list", {
                params: this.params
            }).then(res => {
                if (res.code === '0') {
                    this.tableData = res.data.list;
                    this.total = res.data.total;
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'success'
                    });
                }
            })
        },
        handleSizeChange(pageSize) {
            this.params.pageSize = pageSize;
            this.findBySearch();
        },
        handleCurrentChange(pageNum) {
            this.params.pageNum = pageNum;
            this.findBySearch();
        },
        del(id) {
            request.delete("/hy/" + id).then(res => {
                if (res.code === '0') {
                    this.$message({
                        message: '删除成功',
                        type: 'success'
                    });
                    this.findBySearch();
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'success'
                    });
                }
            })
        },
        openMoveGroupDialog(friend) {
            this.currentFriend = { ...friend };
            this.newGroupName = friend.hyGroupName;
            this.moveGroupDialogVisible = true;
        },
        updateGroup() {
            const updatedFriend = {
                ...this.currentFriend,
                hyGroupName: this.newGroupName
            };
            request.post("/hy/update", updatedFriend).then(res => {
                if (res.code === '0') {
                    this.$message({
                        message: '分组更新成功',
                        type: 'success'
                    });
                    // 更新前端显示的分组名称
                    const index = this.tableData.findIndex(friend => friend.id === this.currentFriend.id);
                    if (index !== -1) {
                        this.tableData[index].hyGroupName = this.newGroupName;
                    }
                    this.moveGroupDialogVisible = false;
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            });
        }
    }
}
</script>
