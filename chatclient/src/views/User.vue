<template>
    <div>
        <div style="margin-bottom: 15px">
            <el-input v-model="params.name" style="width: 200px" placeholder="请输入姓名"></el-input>
            <el-input v-model="params.phone" style="width: 200px; margin-left: 5px" placeholder="请输入电话"></el-input>
            <el-button type="warning" style="margin-left: 10px" @click="findBySearch()">查询</el-button>
            <el-button type="warning" style="margin-left: 10px" @click="reset()">清空</el-button>
            <el-button type="primary" style="margin-left: 10px" @click="add()"
                v-if="user.role === 'ADMIN'">新增</el-button>
        </div>
        <div>
            <el-table border :data="tableData" style="width: 100%">
                <el-table-column align="center" label="头像">
                    <template v-slot="scope">
                        <el-image :src="scope.row.avatar" :preview-src-list="[scope.row.avatar]"
                            style="width: 50px; height: 50px; border-radius: 50%"></el-image>
                    </template>
                </el-table-column>
                <el-table-column align="center" prop="name" label="姓名"></el-table-column>
                <el-table-column align="center" prop="sex" label="性别"></el-table-column>
                <el-table-column align="center" prop="age" label="年龄"></el-table-column>
                <el-table-column align="center" prop="phone" label="电话"></el-table-column>
                <!-- <el-table-column align="center" prop="role" label="分组"></el-table-column> -->
                <el-table-column align="center" label="操作">
                    <template slot-scope="scope">
                        <el-button type="success" plain @click="handleTianjia(scope.row)">添加</el-button>
                        <template v-if="user.role === 'ADMIN'">
                            <el-button type="primary" @click="edit(scope.row)">编辑</el-button>
                            <el-popconfirm title="确定删除吗？" @confirm="del(scope.row.id)">
                                <el-button slot="reference" type="danger" style="margin-left: 5px">删除</el-button>
                            </el-popconfirm>
                        </template>
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
        <div>
            <el-dialog title="请填写信息" :visible.sync="dialogFormVisible" width="30%">
                <el-form :model="form">
                    <el-form-item label="姓名" label-width="15%">
                        <el-input v-model="form.name" autocomplete="off" style="width: 90%"></el-input>
                    </el-form-item>
                    <el-form-item label="性别" label-width="15%">
                        <el-radio v-model="form.sex" label="男">男</el-radio>
                        <el-radio v-model="form.sex" label="女">女</el-radio>
                    </el-form-item>
                    <el-form-item label="年龄" label-width="15%">
                        <el-input v-model="form.age" autocomplete="off" style="width: 90%"></el-input>
                    </el-form-item>
                    <el-form-item label="电话" label-width="15%">
                        <el-input v-model="form.phone" autocomplete="off" style="width: 90%"></el-input>
                    </el-form-item>
                    <el-form-item label="分组" label-width="15%">
                        <el-select v-model="form.role" placeholder="请选择" style="width: 90%">
                            <el-option label="管理员" value="ADMIN"></el-option>
                            <el-option label="用户" value="USER"></el-option>
                        </el-select>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="submit()">确 定</el-button>
                </div>
            </el-dialog>
        </div>
    </div>
</template>

<script>
import request from "@/utils/request";

export default {
    data() {
        return {
            params: {
                name: '',
                phone: '',
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
        /**
         * 添加
         */
        handleTianjia(row) {
            this.$prompt('请输入内容', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
            }).then(({ value }) => {
                let data = {
                    nr: value,
                    hyUserId: row.id,
                }
                request.post("/hy/add", data).then(res => {
                    if (res.code === '0') {
                        this.$message.success("提交成功!");
                    } else {
                        this.$message.error(res.msg);
                    }
                })
            }).catch(() => {
            })
        },
        findBySearch() {
            request.get("/user/search", {
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
        add() {
            this.form = {};
            this.dialogFormVisible = true;
        },
        edit(obj) {
            this.form = obj;
            this.dialogFormVisible = true;
        },
        reset() {
            this.params = {
                pageNum: 1,
                pageSize: 5,
                name: '',
                phone: ''
            }
            this.findBySearch();
        },
        handleSizeChange(pageSize) {
            this.params.pageSize = pageSize;
            this.findBySearch();
        },
        handleCurrentChange(pageNum) {
            this.params.pageNum = pageNum;
            this.findBySearch();
        },
        submit() {
            request.post("/user", this.form).then(res => {
                if (res.code === '0') {
                    this.$message({
                        message: '操作成功',
                        type: 'success'
                    });
                    this.dialogFormVisible = false;
                    this.findBySearch();
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })
        },
        del(id) {
            request.delete("/user/" + id).then(res => {
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
