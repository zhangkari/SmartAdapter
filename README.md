# SmartAdapter

**SmartAdapter** 扩展RecyclerView.Adapter, 用来支持多种数据类型.  
任何问题或建议欢迎联系我 (zhangkaric@gmail.com).

[![Download](https://api.bintray.com/packages/zhangkari/maven/smartadapter/images/d    ownload.svg) ](https://bintray.com/zhangkari/maven/smartadapter/_latestVersion)
## 使用方法:
``` java
implementation 'com.tomtom.widget.smartadapter:smartadapter:1.0.6'

smartAdapter.register(T.class, ViewBinder)
smartAdapter.refreshData()
```
## 效果演示:   
- **简单类型**
- **复杂类型**

## 支持功能:  
- **视图渲染完全解耦** 
- **同时支持多种数据类型** 
- **支持显示优先级(todo)** 
- **支持显示分组(todo)**

## 依赖:   
- **RecyclerView**
