<template>
  <div class="dashboard-container">
    <div class="container">
      <div class="tableBar">
        <label style="margin-right: 5px">套餐名称:</label>
        <el-input v-model="name" placeholder="请输入套餐名称..." style="width: 15%" />
        <label style="margin-right: 5px">套餐分类:</label>
        <el-select v-model="categoryId" placeholder="请选择">
          <el-option
            v-for="item in options"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
        <label style="margin-right: 5px">售卖状态:</label>
        <el-select v-model="status" placeholder="请选择">
          <el-option
            v-for="item in statusArray"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
        <el-button type="primary" style="margin-left: 25px" @click="pageQuery()">查询</el-button>
        <div style="float: right">
          <el-button type="danger" @click="handleDelete('Banch')">批量删除</el-button>
          <el-button type="primary" @click="handleAdd">+新建套餐</el-button>
        </div>
      </div>
      <el-table :data="records" stripe class="tableBox" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="25" />
        <el-table-column prop="name" label="套餐名称" />
        <el-table-column label="图片">
          <template slot-scope="scope">
            <el-image style="width: 80px; height: 40px; border: none" :src="scope.row.image"></el-image>
          </template>
        </el-table-column>
        <el-table-column prop="categoryName" label="套餐分类" />
        <el-table-column prop="price" label="套餐价"/>
        <el-table-column label="售卖状态">
          <template slot-scope="scope">
            <div class="tableColumn-status" :class="{ 'stop-use': scope.row.status === 0 }">
              {{ scope.row.status === 0 ? '停售' : '启售' }}
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="updateTime" label="最后操作时间" />
        <el-table-column label="操作" align="center" width="250px">
          <template slot-scope="scope">
            <el-button type="text" size="small"> 修改 </el-button>
            <el-button type="text" size="small" @click="handleStartOrStop(scope.row)">
              {{ scope.row.status == '1' ? '停售' : '启售' }}
            </el-button>
            <el-button type="text" size="small" @click="handleDelete('Single',scope.row.id)"> 删除 </el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination class="pageList"
                     :page-sizes="[10, 20, 30, 40]"
                     :page-size="pageSize"
                     layout="total, sizes, prev, pager, next, jumper"
                     :total="total"
                     @size-change="handleSizeChange"
                     @current-change="handleCurrentChange" />
    </div>
  </div>
</template>

<script lang="ts">
import {getCategoryByType} from '@/api/category'
import {getSetmealPage, enableOrDisableSetmeal, deleteSetmeal} from '@/api/setMeal'

export default {
  data() {
    return {
      name: '', // 套餐名称，对应查询输入框
      page: 1, // 页码
      pageSize: 10, // 每页显示条数
      total: 0, // 总记录数
      records: [], // 当前页要展示的数据集合
      options: [],
      categoryId: '',
      statusArray: [{
        value: '0',
        label: '停售'
      }, {
        value: '1',
        label: '起售'
      }],
      status: '', // 售卖状态
      multipleSelections: '' // 选中的多个套餐
    }
  },
  created() {
    getCategoryByType({type: 2}).then(res => {
      if (res.data.code === 1) {
        this.options = res.data.data
      }
    })

    // 页面初始化时，调用分页查询
    this.pageQuery()
  },
  methods: {
    //新增套餐，跳转到新增页面（组件）
    handleAdd() {
      this.$router.push('/setmeal/add')
    },
    // 复选框选中事件
    handleSelectionChange(val) {
      this.multipleSelections = val
    },
    // 删除套餐
    handleDelete(type: string, id: string) {
      if (type === 'Banch' && this.multipleSelections.length === 0) {
        this.$message.warning('请选择要删除的套餐！')
        return
      }
      // 弹出确认框，提示用户是否确认
      this.$confirm('确认要调整当前套餐的状态吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let param = ''
        if (type === 'Banch') {
          // 批量删除套餐
          const arr = new Array // 放置选中的套餐id
          this.multipleSelections.forEach(item => {
            arr.push(item.id)
          })
          param = arr.join(',')
        } else {
          // 删除单一套餐
          param = id
        }
        deleteSetmeal(param).then(res => {
          if (res.data.code === 1) {
            this.$message.success('删除成功！')
            this.pageQuery()
          } else {
            this.$message.error(res.data.msg)
          }
        })
      })
    },
    // 套餐的起售停售
    handleStartOrStop(row) {
      // 构造请求参数
      const params = {
        id: row.id,
        status: row.status === 0 ? 1 : 0
      }
      // 弹出确认框，提示用户是否确认
      this.$confirm('确认要调整当前套餐的状态吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        enableOrDisableSetmeal(params).then(res => {
          if (res.data.code === 1) {
            this.$message.success('售卖状态修改成功！')
            this.pageQuery()
          }
        })
      })
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.pageQuery()
    },
    handleCurrentChange(val) {
      this.page = val
      this.pageQuery()
    },
    // 分页查询
    pageQuery() {
      // 封装分页查询对象
      const params = {
        name: this.name,
        categoryId: this.categoryId,
        status: this.status,
        page: this.page,
        pageSize: this.pageSize
      }
      getSetmealPage(params).then(res => {
        if (res.data.code === 1) {
          this.records = res.data.data.records
          this.total = res.data.data.total
        }
      })
    }
  }
}
</script>
<style lang="scss">
.el-table-column--selection .cell {
  padding-left: 10px;
}
</style>
<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;

    .container {
      background: #fff;
      position: relative;
      z-index: 1;
      padding: 30px 28px;
      border-radius: 4px;

      .tableBar {
        margin-bottom: 20px;
        .tableLab {
          float: right;
          span {
            cursor: pointer;
            display: inline-block;
            font-size: 14px;
            padding: 0 20px;
            color: $gray-2;
          }
        }
      }

      .tableBox {
        width: 100%;
        border: 1px solid $gray-5;
        border-bottom: 0;
      }

      .pageList {
        text-align: center;
        margin-top: 30px;
      }
      //查询黑色按钮样式
      .normal-btn {
        background: #333333;
        color: white;
        margin-left: 20px;
      }
    }
  }
}
</style>
