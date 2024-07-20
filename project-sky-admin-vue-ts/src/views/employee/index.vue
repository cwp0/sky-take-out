<template>
  <div class="dashboard-container">
    <div class="container">
      <div class="tableBar">
        <label style="margin-right: 5px">员工姓名:</label>
        <el-input v-model="name" placeholder="请输入员工姓名..." style="width: 15%" />
        <el-button type="primary" style="margin-left: 25px" @click="pageQuery()">查询</el-button>
        <el-button type="primary" style="float: right">+添加员工</el-button>
      </div>
      <el-table
        :data="records"
        stripe
        style="width: 100%">
        <el-table-column
          prop="name"
          label="员工姓名"
          width="180">
        </el-table-column>
        <el-table-column
          prop="username"
          label="账号"
          width="180">
        </el-table-column>
        <el-table-column
          prop="phone"
          label="手机号">
        </el-table-column>
        <el-table-column
          prop="status"
          label="账号状态">
          <template slot-scope="scope">
            {{scope.row.status === 1 ? '启用' : '禁用'}}
          </template>
        </el-table-column>
        <el-table-column
          prop="updateTime"
          label="最后操作时间">
        </el-table-column>
        <el-table-column
          prop="options"
          label="操作">
          <template slot-scope="scope">
            <el-button type="text" size="small">修改</el-button>
            <el-button type="text" size="small" @click="handleStartOrStop(scope.row)">{{scope.row.status === 0 ? '启用' : '禁用'}}</el-button>
            <el-button type="text" size="small">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        class="pageList"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="page"
        :page-sizes="[10, 20, 30, 40, 50]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>
  </div>
</template>
<script lang="ts">
import {getEmployeeList, enableOrDisableEmployee} from '@/api/employee'

export default  {
  data() {
    return {
      name: '', // 员工姓名，对应查询输入框
      page: 1, // 页码
      pageSize: 10, // 每页显示条数
      total: 0, // 总记录数
      records: [] // 当前页要展示的数据集合
    }
  },
  created() { // vue的生命周期方法，当页面加载时自动调用created方法
    this.pageQuery()
  },
  methods: {
    // 启用或禁用员工账号
    handleStartOrStop(row: any) {
      if (row.username === 'admin') {
        this.$message.error('admin为系统的管理员账号，不能更改状态!')
        return
      }

      // alert(`操作员工：${row.id}，状态：${row.status === 1 ? '启用' : '禁用'}`)

      // 弹出确认框，提示用户是否确认修改员工账号状态
      this.$confirm('确认要修改当前员工的状态吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 需要构造和row.id相反的状态值，调用后端接口，修改员工账号状态
        const params = {
          id: row.id,
          status: row.status === 1 ? 0 : 1
        }
        enableOrDisableEmployee(params).then(res => {
          if (res.data.code === 1) {
            this.$message.success('修改成功')
            this.pageQuery()
          }
        }).catch(err => {
          this.$message.error('请求出错了' + err.message)
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消操作'
        });
      });
    },
    // 页码page发生变化时触发
    handleCurrentChange(val: number) {
      this.page = val
      this.pageQuery()
    },
    // pageSize发生变化时触发
    handleSizeChange(val: number) {
      this.pageSize = val
      this.pageQuery()
    },
    pageQuery() {
      const params = {name: this.name, page: this.page, pageSize: this.pageSize}
      // 调用方法ajax请求，访问后端服务，获取分页数据
      getEmployeeList(params).then(res => {
        if (res.data.code === 1) {
          this.total = res.data.data.total
          this.records = res.data.data.records
        }
      }).catch(err => {
        this.$message.error('请求出错了' + err.message)
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.disabled-text {
  color: #bac0cd !important;
}
</style>
