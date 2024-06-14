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
                <el-table-column align="center" prop="nr" label="内容"></el-table-column>
                <el-table-column align="center" prop="zt" label="状态">
                    <template #default="{ row }">
                        <span v-if="row.zt === '0'">申请中</span>
                        <span v-if="row.zt === '1'">已通过</span>
                        <span v-if="row.zt === '2'">已拒绝</span>
                    </template>
                </el-table-column>
                <el-table-column align="center" label="操作">
                    <template slot-scope="scope">
                        <el-button type="success" plain @click="handleUpdateZt(scope.row, '1')"
                            v-if="scope.row.zt === '0'">通过</el-button>
                        <el-button type="danger" plain @click="handleUpdateZt(scope.row, '2')"
                            v-if="scope.row.zt === '0'">拒绝</el-button>
                        <el-popconfirm title="确定删除吗？" @confirm="del(scope.row.id)" v-if="scope.row.zt === '2'">
                            <el-button slot="reference" type="danger" style="margin-left: 5px">删除</el-button>
                        </el-popconfirm>
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
            form: {}
        }
    },
    // 页面加载的时候，做一些事情，在created里面
    created() {
        this.findBySearch();
    },
    // 定义一些页面上控件出发的事件调用的方法
    methods: {
        findBySearch() {
            request.get("/hy/hyyzlist", {
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
        handleUpdateZt(row, zt) {
            let data = { ...row }
            data.zt = zt;
            this.$confirm('是否确定', '提示').then(({ value }) => {
                request.post("/hy/update", data).then(res => {
                    if (res.code === '0') {
                        this.$message.success("提交成功!");
                    } else {
                        this.$message.error(res.msg);
                    }
                    this.findBySearch();
                })
            }).catch(() => {
            })
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
        }
    }
}
</script>
