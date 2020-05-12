## Thrift IDL

### 原有数据类型

#### 基本类型
- 布尔型：bool
- 整型：byte、i16、i32、i64
- 浮点型：double
- 字符串：string（utf8编码）

#### 容器类型
- 有序可重复：list<t>（对应Java的ArrayList）
- 无序不可重复：set<t>（对应Java的HashSet）
- 关联容器：map<k,v>（对应Java的HashMap）

### 可定制数据类型
#### 结构体类型
由struct关键字定义，需要给字段提供数字标签，示例如下：
```
struct PersonVo{
    1:required i32 id,
    2:required string name,
    3:required double wealth
}
```
说明：
1. 字段可以采用=来设置默认值。
2. 数字标签不要轻易修改。在修改时可以考虑新加一个数字标签，丢弃原有的字段。
3. 字段前标识required表示，该字段必填，自然传输时必然会序列化；字段前无标识，表示可以不填充，但一定会序列化；字段前标识optional表示该字段，可以不填充，并且不填充也不会序列化。

