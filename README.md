# RepositorySearchApp

# 프로젝트 소개
GitHub API를 사용하여 GitHub Repository를 조회하고 리스틀 보여주는 어플리케이션

# Preview
https://user-images.githubusercontent.com/50603273/171124731-4ac91883-f518-4fdf-ac32-0f04245e2a07.mp4

# 주요 기능
Repository 검색기능
```bash
Repository를 검색하여 관련 Repository 리스트를 표시
```

페이징 기능
```bash
한번에 10개의 Repository를 보여주고 스크롤 할 경우 다음 Repository 리스트를 표시 
```

# 코드 설명
### Retrofit2 & okhttp setting
![carbon (16)](https://user-images.githubusercontent.com/50603273/171138782-aa8a63b7-283d-4a14-9a36-0e42d4f59ae4.png)
Http Log를 확인하기 위해 HttpLoggingInterceptor 객체를 생성하고 lvele을 설정한 후 OkhttpClient의 addIntercepter 메서드를 사용하여 호출한다.
Retrofitdpsms GsonConverter를 사용하였고, Object 키워드를 사용하여 싱글턴으로 만들었으며, lazy를 통해서 사용되는 순간에 만들어지게 구현하였다.

### API && DTO Class
![carbon (1)](https://user-images.githubusercontent.com/50603273/171140679-28ea4b05-9d0c-480f-8b6b-07fba64caeca.png)
GitHub API의 응답 데이터 구조에 맞게 DTO Class를 구현하였다.

![carbon](https://user-images.githubusercontent.com/50603273/171140671-117d4f2a-aefd-42d2-8b13-14879be581b1.png)
API 통신을 위한 인터페이스를 정의하였다. 코루틴 스코프 안에서 사용하기 위해 suspend 함수로 선언하였다.

### MVVM Architecture
![carbon (2)](https://user-images.githubusercontent.com/50603273/171141905-b13d5de2-6e3f-4fde-b7fc-be24f898ff4b.png)
먼저 data binding을 사용하기 위해 main.xml 리소스를 <layout> 태그를 적용한다.그리고 <data> 태그 안에 사용할 variable인 MainViewModel을 선언한다.
  
![carbon (7)](https://user-images.githubusercontent.com/50603273/171141964-0682738f-0482-42cc-85e7-63d2da730b29.png)
Repository 리스트의 데이터를 처리할 _itemList를 private으로 선언하고, 외부에서 observe 하기 위한 itemList를 getter로 받습니다.
  
![carbon (15)](https://user-images.githubusercontent.com/50603273/171142010-a5d9f5c2-add8-4167-b6bc-ae5f863571df.png)
ViewModel을 생성하고, ViewModel에서 getter로 설정한 itemList를 관찰하여 변화가 있을때마다 어댑터의 리스트를 갱신하도록 구현합니다.
  
### List Adapter
![carbon (8)](https://user-images.githubusercontent.com/50603273/171147110-4ba11f63-4632-466f-af9c-cea67fa954f3.png)
DiffUtil을 사용하여 ListAdapter를 구현함으로써 AsyncListDiffer 객체 없이도 백그라운드 스레드에서 DiffUtil 작업을 수행하고, 별도의 submitList 메서드를 구현하지 않고 사용할 수 있다.
  
![carbon (9)](https://user-images.githubusercontent.com/50603273/171148065-d4cf5541-584d-4424-a210-53961a3d1890.png)
이후에 설명할 페이징 기능을 구현하기 위해 멀티 뷰홀더를 사용하였다. repository의 정보를 담는 GitHubViewHolder와 페이징 기능에서 쓰일 Progressbar가 있는 LoadingViewHolder로 나눠 구현하였다. (GitHubViewHolder의 item은 databinding을 LoadingViewHolder의 item은 viewBinding을 사용하였다.)
  
![carbon (11)](https://user-images.githubusercontent.com/50603273/171148899-b9ce471c-50bb-408c-8a5b-d8dd1e9af08b.png)
Repository의 정보가 비워져 있는 Loading Item은 Type_Loading으로 repository 정보가 있는 Item들은 TYPE_ITEM으로 뷰타입이 정해지고, 뷰타입에 따라 각각 LoadingViewHolder, GitHubVieHolder로 만들어진다.
  
### 페이징 기능
![carbon (12)](https://user-images.githubusercontent.com/50603273/171149609-4aa2728b-ebb3-4dcc-aea9-2a9a8b105c7a.png)
 Repository 클래스에서는 입력받은 repository 이름과 page의 순서, 그동안 받은 repository의 list를 저장한다.
 입력된 repository의 이름이 같고, type이 PAGING 상수와 같을 경우 page의 순서를 1 늘린다. 아닐 경우에는 새로운 repository를 입력받았다고 판단하여 모든 변수를 초기화한다.
  그렇게 설정된 변수들을 통해 api를 호출하고 받은 list를 itemList에 추가하여 리턴한다.
 
![carbon (7)](https://user-images.githubusercontent.com/50603273/171150421-0b47b734-d886-4eeb-ae00-59e7641597cc.png)
  ViewModel의 serachItem 메서드는 단순 검색인지, 페이징인지에 대한 type을 인자로 받아 Repository 클래스의 함수를 실행하여 itemList를 갱신한다.

![carbon (15)](https://user-images.githubusercontent.com/50603273/171150788-0082013f-6640-4da6-9caf-fd6c4ad4dcc0.png)
  Activity에서는 ViewHodel의 itemList를 관찰하다가 변화가 생기면 itemList에 추가적으로 비워져있는 item(LoadingItem)을 넣고 어댑터의 submitList 메서드를 실행한다.
  
![carbon (13)](https://user-images.githubusercontent.com/50603273/171151150-1ec209da-2da6-4e3f-b9eb-f48ee19a3879.png)
  BindingAdapter를 사용하여 RecyclerView에 viewModel를 넘겨 페이징을 구현하였다.
  아이템의 최하단이 완전히 보이는 가장 밑의 아이템의 position인 lastItemPosition과 실제 item의 갯수인 itemCount(-1)를 비교하여 같고, itemType이 TYPE_LOADGIN일 경우 아래의 deleteLastItem()을 호출하고 viewModel의 searchItem 메서드에 PAGING 상수를 인자로 넣어 실행한다.
  
![carbon (10)](https://user-images.githubusercontent.com/50603273/171152081-d828b708-9b23-4df1-9c55-4fd5640535ae.png)
 GitHubAdapter 안에 구현되어 있는 deleteLastItem은 LoadingItem을 삭제하기 위해 구현하였다.
 현재 List를 복사하고 마지막 item을 삭제한 후 다시 submitList 메서드를 사용하여 리스트를 갱신한다.
  
### 이외의 기능들
[키보드 내리기]
![carbon (14)](https://user-images.githubusercontent.com/50603273/171152561-b3c29915-9173-4363-a5e7-75f8d0815be9.png)
 키보드의 엔터버튼 클릭 시 키보드가 내려가고 viewModel의 searchItem 메서드가 실행될 수 있도록 BindingAdapter로 구현하였다.
 getSystemService를 사용하기 위해 context를 받아 구현하였다.

[main.xml]
 ![carbon (3)](https://user-images.githubusercontent.com/50603273/171153337-893f7906-b0bc-4e21-a570-7e82316f1fe5.png)
  EditText에 입력된 값을 viewModel에서도 사용하기 위해 양방향 데이터바인딩으로 구현하였다.(바인딩어댑터의 속성도 사용하였다.)
  위에서 언급하였던 키보드 내리기 기능을 사용하기 위해 imeOptions를 actionDone으로 설정하였다.
  
 ![carbon (4)](https://user-images.githubusercontent.com/50603273/171153344-653de402-b8a4-4fa8-9c0b-07e593450462.png)
  EditText 위에 있는 ImagView로 클릭 시 viewModel의 searchItem이 실행하도록 구현하였다.
  
 ![carbon (5)](https://user-images.githubusercontent.com/50603273/171153346-7aeb78a4-f075-442c-b00e-2c0ecba71f89.png)
 구현한 바인딩어댑터의 속성을 사용하였다.
  
[item_repository.xml]
 ![carbon (6)](https://user-images.githubusercontent.com/50603273/171154368-ffef48d1-1a51-44dd-82d5-cfd75710c3a4.png)
  Repository의 정보를 담는 item에서 이름을 표시하는 TextView에 이름이 1줄을 초과할 경우 흐르는 효과를 주기 위해 marquee 효과와 지금은 deprecated된 singleLine을 사용하여 구현하였다.(databinding을 통해 구현하였다.)

[GitHubViewHolder]
  ![carbon](https://user-images.githubusercontent.com/50603273/171155035-4d76012e-e115-46bc-89c5-ebbffc17f7d1.png) 
  ViewHolder에서 bind할 때, databinding된 item에 값을 넣어주고, image의 경우에는 Glide를 통해 로드하도록 구현하였다.
  isSelected를 true로 한 것은 marguee 효과를 주기 위해 설정하였다.

# Specification
<table class="tg">
<tbody>
  <tr>
    <td><b>Architecture</b></td>
    <td>MVVM</td>
  </tr>
<tr>
    <td><b>Jetpack Components</b></td>
<td>DataBinding, LiveData, ViewModel </td>
</tr>
  <tr>
    <td><b>Other Libraries</b></td>
<td>Glide</td>
</tr>
<tr>
    <td><b>Network</b></td>
<td>OkHttp, Retrofit2, coroutine</td>
</tr>
<tr>
    <td><b>minSdk/targetSdk</b></td>
<td>23/31</td>
</tr>
</tbody>
</table>

# Package Structure
```bash
* 📦repositorysearchapp
        ├─📂main
        ├─📂network
        │  ├─📂api
        │  ├─📂dto
        │  └─📂repository
        └─📂util
```
  
