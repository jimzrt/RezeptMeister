(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([[2],{"0c66":function(e,t,a){},"0d77":function(e,t,a){"use strict";a.r(t);var i=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("q-layout",{attrs:{view:"hHh Lpr lff"}},[a("q-ajax-bar",{attrs:{position:"bottom",color:"primary",size:"10px"}}),a("q-page-container",[a("q-header",{staticClass:"text-grey-1 q-py-xs edekaBar",attrs:{"height-hint":"65"}},[a("q-toolbar",[a("q-btn",{attrs:{flat:"","no-caps":"","no-wrap":"",padding:"0px 5px 0px 10px"}},[a("img",{attrs:{src:e.api+"/images/logo.png",height:"50px"}}),e.$q.screen.gt.sm?a("q-toolbar-title",{staticClass:"text-weight-bold my-font",staticStyle:{"padding-right":"20px"},attrs:{shrink:""}},[e._v("\n            RezeptMeister\n          ")]):e._e()],1),a("div",{staticClass:"toolio",class:{inactive:!e.searchFocus&&e.searchFilterEmpty},style:e.$q.screen.gt.sm?"":"left:80px;right:10px"},[a("q-input",{ref:"searchBar",staticClass:"YL__toolbar-input",class:{active:e.searchFocus||!e.searchFilterEmpty},attrs:{dense:"",standout:"bg-primary",placeholder:"Zutat oder Gericht","bottom-slots":"",debounce:"200"},on:{keyup:[function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"down",40,t.key,["Down","ArrowDown"])?null:e.selectNextSuggestion(t)},function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"up",38,t.key,["Up","ArrowUp"])?null:e.selectPrevSuggestion(t)}]},scopedSlots:e._u([{key:"prepend",fn:function(){return[""===e.searchInput?a("q-icon",{staticClass:"justify-center",attrs:{name:"search"}}):a("q-icon",{staticClass:"cursor-pointer",attrs:{name:"clear"},on:{click:function(t){e.searchInput=""}}})]},proxy:!0},{key:"hint",fn:function(){return[a("transition",{attrs:{name:"slide-fade"}},[a("SearchResultList",{directives:[{name:"show",rawName:"v-show",value:e.searchFocus,expression:"searchFocus"}],ref:"searchResult",attrs:{search:e.searchInput,searchFilter:e.searchFilter},on:{onAddToSearch:e.addToSearch,onClearSearch:function(t){e.searchInput="",e.searchFocus=!1}}})],1)]},proxy:!0}]),model:{value:e.searchInput,callback:function(t){e.searchInput=t},expression:"searchInput"}})],1),e.$q.screen.gt.sm?[a("q-space"),a("q-toggle",{attrs:{value:e.$q.dark.isActive,label:"Dark Mode"},on:{input:function(t){return e.$q.dark.toggle()}}})]:e._e()],2)],1),a("RecipeResultGrid",{on:{onAddToSearch:e.addToSearch},model:{value:e.searchFilter,callback:function(t){e.searchFilter=t},expression:"searchFilter"}}),e.searchFilterEmpty?e._e():a("SearchFilterBar",{on:{onAddToSearch:e.addToSearch,onRemoveFromSearch:e.removeFromSearch,onClearSearch:function(t){e.searchFilter={}}},model:{value:e.searchFilter,callback:function(t){e.searchFilter=t},expression:"searchFilter"}})],1)],1)},r=[],s=(a("c975"),a("a434"),function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"searchResult"},[""==this.search?void 0:[a("q-list",{attrs:{bordered:"",separator:"",padding:""}},[a("q-item-label",{staticClass:"heading",attrs:{header:""}},[e._v("Zutaten")]),e.loading?e._l(7,(function(e){return a("q-item",{key:e+"I",staticStyle:{"max-width":"400px"}},[a("q-item-section",{attrs:{avatar:""}},[a("q-skeleton",{attrs:{size:"40px",type:"QAvatar"}})],1),a("q-item-section",[a("q-item-label",[a("q-skeleton",{attrs:{type:"text"}})],1),a("q-item-label",{attrs:{caption:""}},[a("q-skeleton",{attrs:{type:"text",width:"65%"}})],1)],1)],1)})):[e.ingredients.length>0?e._l(e.ingredients,(function(t,i){return a("q-item",{directives:[{name:"ripple",rawName:"v-ripple"}],key:t.slug,staticClass:"list-complete-item",attrs:{clickable:"",active:e.selectedIndex==i,"active-class":"bg-blue-1"},on:{click:function(a){return e.addToSearch("ingredient",t)}}},[a("q-item-section",{attrs:{avatar:""}},[a("q-avatar",{attrs:{circle:"",size:"40px"}},[a("q-img",{attrs:{src:e.api+t.pictureUrl}})],1)],1),a("q-item-section",{attrs:{"no-wrap":""}},[a("div",{staticClass:"row"},[a("div",{staticClass:"col-8"},[a("h6",{staticClass:"resultHeading"},[a("span",{domProps:{innerHTML:e._s(e.makeBold(t.name))}})])])])]),a("q-item-section",{attrs:{side:""}},[void 0==t.recipeCount?a("q-spinner-dots",{attrs:{color:"primary",size:"40px"}}):a("q-chip",[a("q-avatar",{attrs:{color:"primary","text-color":"white","font-size":"14px"}},[e._v(e._s(t.recipeCount))]),e._v("\n                Rezept"+e._s(1!=t.recipeCount?"e":"")+"\n              ")],1)],1)],1)})):e._e(),a("q-item",{directives:[{name:"ripple",rawName:"v-ripple"}],staticClass:"list-complete-item",attrs:{clickable:"",active:0==e.selectedIndex,"active-class":"bg-blue-1"},on:{click:function(t){return e.addToSearch("ingredient_special",{id:e.search,name:"*"+e.search+"*"})}}},[a("q-item-section",{attrs:{avatar:""}},[a("q-avatar",{attrs:{circle:"",size:"40px"}},[a("q-img",{attrs:{src:e.api+"/images/wildcard.png"}})],1)],1),a("q-item-section",{attrs:{"no-wrap":""}},[a("div",{staticClass:"row"},[a("div",{staticClass:"col-8"},[a("h6",{staticClass:"resultHeading"},[a("span",{domProps:{innerHTML:e._s("Alle Zutaten die '"+e.makeBold(this.search)+"' enthalten")}})])])])]),a("q-item-section",{attrs:{side:""}},[-1==e.specialRecipeCount?a("q-spinner-dots",{attrs:{color:"primary",size:"40px"}}):a("q-chip",[a("q-avatar",{attrs:{color:"primary","text-color":"white","font-size":"14px"}},[e._v(e._s(e.specialRecipeCount))]),e._v("\n              Rezept"+e._s(1!=e.specialRecipeCount?"e":"")+"\n            ")],1)],1)],1)],a("q-item-label",{staticClass:"heading",attrs:{header:""}},[e._v("Kategorien")]),a("q-item",[a("q-item-section",[e.tags.length>0?a("div",e._l(e.tags,(function(t){return a("q-chip",{key:t.id,attrs:{clickable:"",color:"primary","text-color":"white"},on:{click:function(a){return e.addToSearch("tag",t)}}},[a("span",{domProps:{innerHTML:e._s(e.makeBold(t.name))}})])})),1):this.loading?a("div",e._l(7,(function(e){return a("q-skeleton",{key:e+"T",staticStyle:{display:"inline-flex","margin-right":"10px"},attrs:{type:"QChip"}})})),1):e._e()])],1),a("q-item-label",{staticClass:"heading",attrs:{header:""}},[e._v("Rezepte")]),e.recipes.length>0?e._l(e.recipes,(function(t){return a("q-item",{directives:[{name:"ripple",rawName:"v-ripple"}],key:t.seoTitle,staticClass:"list-complete-item",attrs:{clickable:"",to:{name:"recipe",params:{recipeSeoTitle:t.seoTitle,recipe:t}}}},[a("q-item-section",{attrs:{avatar:""}},[a("q-avatar",{attrs:{circle:"",size:"40px"}},[a("q-img",{attrs:{src:e._f("backendPictureUrl")(t),ratio:1}})],1)],1),a("q-item-section",{attrs:{"no-wrap":""}},[a("h6",{staticClass:"resultHeading"},[a("span",{domProps:{innerHTML:e._s(e.makeBold(t.title))}})])])],1)})):this.loading?e._l(3,(function(e){return a("q-item",{key:e+"R",staticStyle:{"max-width":"400px"}},[a("q-item-section",{attrs:{avatar:""}},[a("q-skeleton",{attrs:{size:"40px",type:"QAvatar"}})],1),a("q-item-section",[a("q-item-label",[a("q-skeleton",{attrs:{type:"text"}})],1),a("q-item-label",{attrs:{caption:""}},[a("q-skeleton",{attrs:{type:"text",width:"65%"}})],1)],1)],1)})):e._e()],2)]],2)}),n=[],c=(a("e6cf"),a("ddb0"),a("8df4")),o=a.n(c),l=(a("2ef0"),{methods:{flatten(e){if(_.isArray(e))return e.map((e=>this.flatten(e)));if(_.isPlainObject(e)){if("id"in e)return e.id;let t={};return Object.entries(e).forEach((([e,a])=>t[e]=this.flatten(a))),t}return e},deepCheck(e){return _.isArray(e)?_.every(e.map((e=>this.deepCheck(e)))):_.isPlainObject(e)?_.every(Object.values(e).map((e=>this.deepCheck(e)))):0==e||""==e||void 0==e}}}),d={name:"SearchResultList",props:{search:{type:String,required:!0},searchFilter:{type:Object,required:!0}},mixins:[l],data(){return{selectedIndex:-1,ingredients:[],recipes:[],tags:[],loading:!1,cancelToken:o.a.source(),promises:[],api:"",specialRecipeCount:-1}},computed:{searchFilterEmpty(){return this.deepCheck(this.value)},flatFilter(){return this.flatten(this.searchFilter)}},filters:{backendPictureUrl(e){return"/images/recipe/"+e.seoTitle+"_big.jpg"}},methods:{makeBold(e){if(!e)return"";const t=e.toLowerCase(),a=this.search.toLowerCase(),i=t.indexOf(a);let r="";return r+=e.substring(0,i),r+="<span class='text-weight-bolder' style='text-decoration: underline'>"+e.substring(i,i+a.length)+"</span>",r+=e.substring(i+a.length,e.length),r},addToSearch(e,t){this.$emit("onAddToSearch",e,t)},selectNext(){this.selectedIndex++},selectPrev(){this.selectedIndex--},async loadData(e){this.cancelToken.cancel();let t=await Promise.all(this.promises);for(let i of t)Array.isArray(i)&&await Promise.all(i);if(this.cancelToken=o.a.source(),this.selectedIndex=-1,this.ingredients=[],this.recipes=[],this.tags=[],this.promises=[],""==e)return;this.loading=!0;let a=this.promises.length;this.promises[a]=this.$axios.get("/ingredient",{cancelToken:this.cancelToken.token,params:{name:e,count:5}}).then((e=>{let t=[];this.ingredients=e.data;for(let a=0;a<this.ingredients.length;a++)t[t.length]=this.$axios.post("/recipe/count/id/"+this.ingredients[a].id,this.flatFilter,{cancelToken:this.cancelToken.token}).then((e=>{this.ingredients[a].recipeCount=e.data,this.$set(this.ingredients,a,this.ingredients[a])})).catch((()=>{console.log("aborted")}));return this.search&&(t[t.length]=this.$axios.post("/recipe/count/name/"+this.search,this.flatFilter,{cancelToken:this.cancelToken.token}).then((e=>{this.specialRecipeCount=e.data})).catch((()=>{console.log("aborted")}))),t})).catch((()=>{this.$q.notify({color:"negative",position:"top",message:"Loading failed",icon:"report_problem"})})),this.promises[this.promises.length]=this.$axios.get("/recipe",{cancelToken:this.cancelToken.token,params:{name:e,count:3}}).then((e=>{console.log(e.data),this.recipes=e.data})).catch((()=>{this.$q.notify({color:"negative",position:"top",message:"Loading failed",icon:"report_problem"})})),this.promises[this.promises.length]=this.$axios.get("/tag",{params:{cancelToken:this.cancelToken.token,name:e,count:5}}).then((e=>{console.log(e.data),this.tags=e.data})).catch((()=>{this.$q.notify({color:"negative",position:"top",message:"Loading failed",icon:"report_problem"})})),Promise.all(this.promises).then((e=>{this.loading=!1}))}},mounted:function(){this.loadData(this.search)},watch:{"$props.search":{handler:function(e,t){this.specialRecipeCount=-1,this.loadData(this.search)},deep:!0}}},h=d,p=(a("d7ca"),a("1313"),a("2877")),u=a("4983"),m=a("1c1c"),f=a("0170"),g=a("66e5"),v=a("4074"),x=a("cb32"),y=a("068f"),b=a("8380"),q=a("b047"),k=a("293e"),w=a("b498"),C=a("714f"),S=a("eebe"),F=a.n(S),T=Object(p["a"])(h,s,n,!1,null,"3326f294",null),Q=T.exports;F()(T,"components",{QScrollArea:u["a"],QList:m["a"],QItemLabel:f["a"],QItem:g["a"],QItemSection:v["a"],QAvatar:x["a"],QImg:y["a"],QSpinnerDots:b["a"],QChip:q["a"],QSkeleton:k["a"],QColor:w["a"]}),F()(T,"directives",{Ripple:C["a"]});var R=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",[a("q-page-sticky",{staticStyle:{transform:"translate(0px, 60px)"},attrs:{expand:"",position:"top"}},[a("q-toolbar",{staticClass:"toolbar shadow-1",staticStyle:{"white-space":"nowrap"}},[a("div",{ref:"searchFilterBar",staticStyle:{display:"flex","flex-grow":"1","align-items":"center"}},[e.isOverflowing?a("q-btn",{staticStyle:{height:"56px"},attrs:{flat:"",icon:"filter_alt",label:"Filter"},on:{click:function(t){e.drawer=!e.drawer}}}):[e._l(e.nonEmptyFilter,(function(t,i){return a("div",{key:t},[i>0?[a("q-separator",{attrs:{vertical:""}})]:e._e(),[a("q-btn",{attrs:{stretch:"",flat:"",label:e._f("canonicalName")(t)}}),e._l(e.value[t],(function(i){return a("q-chip",{key:i.id,attrs:{square:"",removable:"",clickable:"",color:"ingredient"==t?"primary":"secondary","text-color":"white"},on:{click:function(a){return e.addToSearch(t,i,!1,!0)},remove:function(a){return e.removeFromSearch(t,i)}}},[e._v(e._s(i.name))])})),e.value["exclude"]?e._l(e.value["exclude"][t],(function(i){return a("q-chip",{key:i.id,attrs:{square:"",removable:"",clickable:"",color:"red","text-color":"white"},on:{click:function(a){return e.addToSearch(t,i,!1,!0)},remove:function(a){return e.removeFromSearch(t,i)}}},[e._v(e._s(i.name))])})):e._e()]],2)})),a("q-select",{staticStyle:{"padding-left":"10px","min-width":"220px"},attrs:{filled:"",stretch:"",options:e.options,label:"Schwierigkeit",multiple:"","emit-value":"",value:e.model},on:{input:e.difficultyChanged},scopedSlots:e._u([{key:"option",fn:function(t){return[a("q-item",e._g(e._b({},"q-item",t.itemProps,!1),t.itemEvents),[a("q-item-section",[a("q-item-label",{domProps:{innerHTML:e._s(t.opt)}})],1),a("q-item-section",{attrs:{side:""}},[a("q-toggle",{attrs:{value:e.model,val:t.opt},on:{input:e.difficultyChanged}})],1)],1)]}}])}),a("q-btn-dropdown",{attrs:{flat:"",filled:"",stretch:"","no-caps":""},scopedSlots:e._u([{key:"label",fn:function(){return[a("div",{staticClass:"row items-center no-wrap",style:e.standard>0?"":e.$q.dark.isActive?"color: rgba(255,255,255,0.7);":"color: rgba(0, 0, 0, 0.6);"},[a("q-icon",{attrs:{left:"",name:"local_fire_department"}}),a("div",{staticClass:"text-center"},[e._v("\n                  "+e._s("Max Kcal"+(e.standard>0?" ("+e.standard+")":""))+"\n                ")])],1)]},proxy:!0}])},[a("div",{staticClass:"q-pa-md"},[a("q-slider",{staticStyle:{"min-width":"280px","margin-top":"10px"},attrs:{value:e.standard,min:0,max:e.maxCalories,"label-always":""},on:{change:e.caloriesChanged}})],1)])],a("q-space"),a("q-btn",{attrs:{stretch:"",flat:"",icon:"clear",label:"Filter löschen"},on:{click:function(t){return e.$emit("onClearSearch")}}})],2)])],1),e.isOverflowing?a("q-drawer",{attrs:{width:300,overlay:"",bordered:""},model:{value:e.drawer,callback:function(t){e.drawer=t},expression:"drawer"}},[a("q-scroll-area",{staticClass:"fit"},[[e._l(e.nonEmptyFilter,(function(t,i){return a("div",{key:t,staticStyle:{display:"flex","flex-direction":"column","align-items":"center",gap:"6px"}},[i>0?[a("q-separator")]:e._e(),[a("q-btn",{attrs:{stretch:"",flat:"",label:e._f("canonicalName")(t)}}),e._l(e.value[t],(function(i){return a("q-chip",{key:i.id,attrs:{square:"",removable:"",clickable:"",color:"ingredient"==t?"primary":"secondary","text-color":"white"},on:{click:function(a){return e.addToSearch(t,i,!1,!0)},remove:function(a){return e.removeFromSearch(t,i)}}},[e._v(e._s(i.name))])})),e.value["exclude"]?e._l(e.value["exclude"][t],(function(i){return a("q-chip",{key:i.id,attrs:{square:"",removable:"",clickable:"",color:"red","text-color":"white"},on:{click:function(a){return e.addToSearch(t,i,!1,!0)},remove:function(a){return e.removeFromSearch(t,i)}}},[e._v(e._s(i.name))])})):e._e()]],2)})),a("q-select",{attrs:{filled:"",stretch:"",options:e.options,label:"Schwierigkeit",multiple:"","emit-value":"",value:e.model},on:{input:e.difficultyChanged},scopedSlots:e._u([{key:"option",fn:function(t){return[a("q-item",e._g(e._b({},"q-item",t.itemProps,!1),t.itemEvents),[a("q-item-section",[a("q-item-label",{domProps:{innerHTML:e._s(t.opt)}})],1),a("q-item-section",{attrs:{side:""}},[a("q-toggle",{attrs:{value:e.model,val:t.opt},on:{input:e.difficultyChanged}})],1)],1)]}}],null,!1,1233613069)}),a("q-btn-dropdown",{staticClass:"full-width",attrs:{flat:"",filled:"",stretch:"","no-caps":""},scopedSlots:e._u([{key:"label",fn:function(){return[a("div",{staticClass:"row items-center no-wrap full-width",style:e.standard>0?"":e.$q.dark.isActive?"color: rgba(255,255,255,0.7);":"color: rgba(0, 0, 0, 0.6);"},[a("q-icon",{attrs:{left:"",name:"local_fire_department"}}),a("div",{staticClass:"text-center"},[e._v("\n                "+e._s("Max Kcal"+(e.standard>0?" ("+e.standard+")":""))+"\n              ")])],1)]},proxy:!0}],null,!1,2822854419)},[a("div",{staticClass:"q-pa-md"},[a("q-slider",{staticStyle:{"min-width":"280px","margin-top":"10px"},attrs:{value:e.standard,min:0,max:e.maxCalories,"label-always":""},on:{change:e.caloriesChanged}})],1)])]],2)],1):e._e()],1)},$=[],P=(a("4e82"),{name:"SearchFilterBar",props:["value"],mounted(){this.mounted=!0,this.checkOverflow()},activated(){this.checkOverflow()},created(){window.addEventListener("resize",this.checkOverflow),document.addEventListener("click",this.onClickAnywhere)},destroyed(){window.removeEventListener("resize",this.checkOverflow),document.removeEventListener("click",this.onClickAnywhere)},computed:{nonEmptyFilter(){let e=Object.keys(this.value).sort().filter((e=>"difficulty"!=e&&"calories"!=e&&"exclude"!=e&&(this.value[e].length>0||this.value["exclude"]&&e in this.value["exclude"]&&this.value["exclude"][e].length>0)));return console.log(e),e}},filters:{canonicalName(e){switch(e){case"ingredient":return"Zutaten";case"recipe":return"Rezepte";case"tag":return"Kategorien";default:return e}}},methods:{onClickAnywhere(e){console.log("clickoo"),this.drawer&&e.target.classList.contains("q-drawer__backdrop")&&(this.drawer=!1)},checkOverflow(){this.isOverflowing=!1,this.$nextTick((()=>{this.$refs.searchFilterBar&&(this.isOverflowing=this.$refs.searchFilterBar.offsetWidth>window.innerWidth,console.log("overflow",this.isOverflowing))}))},englishDifficulty(e){switch(e){case"Einfach":return"EASY";case"Medium":return"MEDIUM";case"Schwer":return"HARD";default:return e}},caloriesChanged(e){this.standard=e,this.addToSearch("calories",this.standard,!0)},difficultyChanged(e){e=e.sort(),this.model.length>e.length?(console.log("remove",_.difference(this.model,e)[0]),this.$emit("onRemoveFromSearch","difficulty",this.englishDifficulty(_.difference(this.model,e)[0]))):(console.log("add",_.difference(e,this.model)[0]),this.$emit("onAddToSearch","difficulty",this.englishDifficulty(_.difference(e,this.model)[0]))),this.model=e},addToSearch(e,t,a=!1,i=!1){console.log("addToSearch",e,t,a,i),this.$emit("onAddToSearch",e,t,a,i)},removeFromSearch(e,t){this.$emit("onRemoveFromSearch",e,t)},log(e,t){this.dataCopy=_.clone(this.value),_.remove(this.dataCopy[e],t),this.updateData()},updateData(){this.$emit("input",this.dataCopy)}},data(){return{mounted:!1,dataCopy:{},maxCalories:1e3,model:[],standard:0,options:["Einfach","Medium","Schwer"],isOverflowing:!1,drawer:!1,menuList:[{icon:"inbox",label:"Inbox",separator:!0},{icon:"send",label:"Outbox",separator:!1},{icon:"delete",label:"Trash",separator:!1},{icon:"error",label:"Spam",separator:!0},{icon:"settings",label:"Settings",separator:!1},{icon:"feedback",label:"Send Feedback",separator:!1},{icon:"help",iconColor:"primary",label:"Help",separator:!1}]}},watch:{"$props.value":{handler:function(e,t){this.checkOverflow()},deep:!0}}}),I=P,A=(a("e23a"),a("de5e")),E=a("65c6"),O=a("9c40"),L=a("eb85"),D=a("ddd8"),z=a("9564"),B=a("f20b"),j=a("0016"),M=a("c1d0"),H=a("2c91"),N=a("9404"),U=a("8572"),K=Object(p["a"])(I,R,$,!1,null,null,null),G=K.exports;F()(K,"components",{QPageSticky:A["a"],QToolbar:E["a"],QBtn:O["a"],QSeparator:L["a"],QChip:q["a"],QSelect:D["a"],QItem:g["a"],QItemSection:v["a"],QItemLabel:f["a"],QToggle:z["a"],QBtnDropdown:B["a"],QIcon:j["a"],QSlider:M["a"],QSpace:H["a"],QDrawer:N["a"],QScrollArea:u["a"],QList:m["a"],QField:U["a"],QColor:w["a"]}),F()(K,"directives",{Ripple:C["a"]});var Z=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",[e.currentRecipes.content&&e.currentRecipes.content.length>0?a("q-banner",{staticClass:"top-banner-pagination",staticStyle:{"margin-top":"55px"}},[a("div",{staticStyle:{display:"flex","flex-grow":"1","align-items":"center"}},[a("div",[e._v("\n     "+e._s(e.currentRecipes.totalElements)+" Rezepte gefunden\n     ")]),a("q-space"),a("q-select",{staticStyle:{width:"150px"},attrs:{value:e.resultCount,options:e.resultsPerPageOptions,label:"Rezepte pro Seite"},on:{input:e.resultPerPageChanged}})],1)]):e._e(),a("transition",{attrs:{name:"list"}},[e.currentRecipes.content&&e.currentRecipes.content.length>0?a("div",{staticClass:"q-pa-l row items-center flex-center q-gutter-xl",staticStyle:{"margin-bottom":"80px","padding-top":"40px"}},e._l(e.currentRecipes.content,(function(t){return a("Flipped",{key:t.id,ref:"test",refInFor:!0,attrs:{"flip-id":t.seoTitle+"_1",scale:"",translate:""}},[a("div",[a("q-card",{staticClass:"my-card",style:e.expandedCards.includes(t.id)?"":"height: 750px;overflow: hidden;"},[a("Flipped",{attrs:{"flip-id":t.seoTitle,scale:"",translate:""}},[a("q-img",{attrs:{src:e._f("backendPictureUrl")(t),ratio:1},on:{click:function(a){return e.openRecipe(t)}}},[a("div",{staticClass:"absolute-top text-subtitle2"},[e._v("\n                   "+e._s(e._f("germanDifficulty")(t.difficulty))+"\n                 ")])])],1),a("q-card-section",[a("div",{staticClass:"row no-wrap items-center"},[a("Flipped",{attrs:{"flip-id":t.seoTitle+"_2",scale:"",translate:""}},[a("div",{staticClass:"text-h6 my-font col ellipsis"},[e._v("\n                     "+e._s(t.title)+"\n                   ")])]),a("div",{staticClass:"col-auto text-grey text-caption q-pt-md column no-wrap items-center"},[a("div",[e._v(e._s(t.calories)+" kcal")]),a("div",[e._v("\n                     "+e._s(new Date(1e3*t.preperationTimeInSeconds).toISOString().substr(11,8))+"\n                   ")])])],1)]),a("q-separator"),a("q-card-section",{attrs:{horizontal:""}},[a("q-card-section",{staticClass:"text-body2",staticStyle:{"align-items":"center",display:"flex","text-align":"justify"}},[e._v("\n                 "+e._s(t.description)+"\n               ")]),a("q-separator",{staticStyle:{"flex-shrink":"0"},attrs:{vertical:""}}),a("q-card-section",{staticClass:"col-4 text-caption",staticStyle:{"text-align":"center"},style:e.expandedCards.includes(t.id)?"":"height:177px;overflow:hidden"},e._l(t.tags,(function(t){return a("q-chip",{key:t.id,staticClass:"clipped",attrs:{dense:"",square:"",clickable:"",color:"secondary","text-color":"white"},on:{click:function(a){return e.addToSearch("tag",t)}}},[e._v("\n                   "+e._s(t.name)+"\n                 ")])})),1)],1),a("q-separator"),a("q-card-section",{attrs:{horizontal:""}},[a("q-card-section",{staticClass:"text-body2"},e._l(t.ingredients,(function(t){return a("q-chip",{key:t.id,attrs:{dense:"",square:"",clickable:"",color:"primary","text-color":"white"},on:{click:function(a){return e.addToSearch("ingredient",t)}}},[e._v("\n                   "+e._s(t.name)+"\n                 ")])})),1)],1)],1),a("q-btn",{staticClass:"full-width expand-button",attrs:{color:"info",icon:e.expandedCards.includes(t.id)?"expand_less":"expand_more"},on:{click:function(a){return e.expandCard(t.id)}}})],1)])})),1):e.searchFilterEmpty||e.loading?e._e():a("div",{staticClass:"absolute-center",staticStyle:{display:"flex","align-items":"center","justify-content":"center"}},[a("h4",[e._v("Keine Rezepte gefunden :(")])])]),e.currentRecipes.content&&e.currentRecipes.content.length>0?[a("q-page-sticky",{staticClass:"pagination-bar",attrs:{expand:"",position:"bottom"}},[a("div",{staticClass:"q-pa-md flex flex-center"},[a("q-pagination",{attrs:{"direction-links":!0,value:e.currentPage,max:e.currentRecipes.totalPages,"max-pages":6,"boundary-numbers":!0,color:"accent"},on:{input:e.changePage}})],1)])]:e._e()],2)},Y=[],J=a("0129"),W={name:"RecipeResultGrid",props:["value"],components:{Flipped:J["Flipped"]},mixins:[l],computed:{searchFilterEmpty(){return this.deepCheck(this.value)},flatFilter(){return this.flatten(this.value)}},filters:{backendPictureUrl(e){return"/images/recipe/"+e.seoTitle+"_big.jpg"},truncate(e){return e.length<=13?e:e.substring(0,10)+"..."},germanDifficulty(e){switch(e){case"EASY":return"Einfach";case"MEDIUM":return"Medium";case"HARD":return"Schwer";default:return e}},canonicalName(e){switch(e){case"ingredient":return"Zutaten";case"recipe":return"Rezepte";case"tag":return"Kategorien";default:return e}}},methods:{expandCard(e){let t=this.expandedCards.indexOf(e);-1==t?this.expandedCards.push(e):this.expandedCards.splice(t,1)},openRecipe(e){this.$router.push({name:"recipe",params:{recipeSeoTitle:e.seoTitle,recipe:e}})},addToSearch(e,t){this.$emit("onAddToSearch",e,t)},styleIngredients(e){return e.map((e=>this.value["ingredient"]&&this.value["ingredient"].some((t=>t.id===e.id))?"<span class='text-weight-bold'>"+e.name+"</span>":e.name)).join(", ")},resultPerPageChanged(e){this.currentPage=1,this.resultCount=e,this.loadData()},changePage(e){this.currentPage!=e&&(this.currentPage=e,this.loadData())},log(e,t){this.dataCopy=_.clone(this.value),_.remove(this.dataCopy[e],t),this.updateData()},updateData(){this.$emit("input",this.dataCopy)},loadData(){this.loading=!0,this.$q.loading.show({delay:0}),console.log("construct searchFilter from",this.value);let e=this.flatFilter;e["page"]=this.currentPage-1,e["count"]=this.resultCount,console.log(e),window.scrollTo(0,0),this.$axios.post("/recipe/search",e).then((e=>{this.currentRecipes=e.data,this.totalElements=e.data.totalElements,console.log(e.data)})).catch((()=>{this.$q.notify({color:"negative",position:"top",message:"Loading failed",icon:"report_problem"})})).then((()=>{this.$q.loading.hide(),this.loading=!1}))}},data(){return{loading:!1,api:"",dataCopy:{},currentRecipes:[],currentPage:1,resultCount:12,totalElements:0,expandedCards:[],resultsPerPageOptions:[12,24,36,48]}},watch:{"$props.value":{handler:function(e,t){if(this.searchFilterEmpty)return console.log("Searchfitler Empty"),this.currentRecipes=[],void(this.currentPage=1);this.currentPage=1,this.loadData()},deep:!0}}},V=W,X=(a("d3bf"),a("e9ef"),a("54e1")),ee=a("f09f"),te=a("a370"),ae=a("3b16"),ie=Object(p["a"])(V,Z,Y,!1,null,"66ddbc99",null),re=ie.exports;F()(ie,"components",{QBanner:X["a"],QSpace:H["a"],QSelect:D["a"],QCard:ee["a"],QImg:y["a"],QCardSection:te["a"],QIcon:j["a"],QSeparator:L["a"],QChip:q["a"],QBtn:O["a"],QPageSticky:A["a"],QPagination:ae["a"],QColor:w["a"]});const se=["Google","Facebook","Twitter","Apple","Oracle"];var ne={name:"RecipeSearch",components:{RecipeResultGrid:re,SearchResultList:Q,SearchFilterBar:G},data(){return{model:null,searchFocus:!1,filterOptions:se,leftDrawerOpen:!0,searchInput:"",selected:"",searchFilter:{},api:""}},created(){document.addEventListener("click",this.onClickAnywhere)},destroyed(){document.removeEventListener("click",this.onClickAnywhere)},computed:{searchFilterEmpty(){let e=function(t){return _.isArray(t)?_.every(t.map((t=>e(t)))):_.isPlainObject(t)?_.every(Object.values(t).map((t=>e(t)))):0==t||""==t||void 0==t};return e(this.searchFilter)}},methods:{addToSearch(e,t,a=!1,i=!1){if(console.log(i?"exclude":"add",e,t),this.searchInput="",a)this.$set(this.searchFilter,e,t);else{if(i){if("exclude"in this.searchFilter||this.$set(this.searchFilter,"exclude",{}),e in this.searchFilter["exclude"]){if(_.some(this.searchFilter["exclude"][e],t))return this.removeFromSearch(e,t),void this.addToSearch(e,t)}else this.$set(this.searchFilter["exclude"],e,[]);return this.removeFromSearch(e,t),void this.searchFilter["exclude"][e].push(t)}if(e in this.searchFilter){if(_.some(this.searchFilter[e],t))return}else this.$set(this.searchFilter,e,[]);console.log("VALUE",t),this.searchFilter[e].push(t)}},removeFromSearch(e,t){console.log("remove",e,t),e in this.searchFilter&&(_.some(this.searchFilter[e],t)||_.includes(this.searchFilter[e],t))?(console.log(t,"is to be removed!"),this.searchFilter[e].splice(this.searchFilter[e].indexOf(t),1)):"exclude"in this.searchFilter&&e in this.searchFilter["exclude"]&&_.some(this.searchFilter["exclude"][e],t)&&(console.log(t,"is to be unremoved!"),this.searchFilter["exclude"][e].splice(this.searchFilter["exclude"][e].indexOf(t),1))},selectNextSuggestion:function(){this.$refs.searchResult.selectNext()},selectPrevSuggestion:function(){this.$refs.searchResult.selectPrev()},onClickAnywhere(e){const t=this.$refs.searchBar.$el;t==e.target||t.contains(e.target)?this.searchFocus=!0:this.searchFocus=!1}}},ce=ne,oe=(a("e40c"),a("80d7"),a("4d5a")),le=a("7ea5"),de=a("09e3"),he=a("e359"),pe=a("6ac5"),ue=a("27f9"),me=a("58a81"),fe=a("05c0"),ge=Object(p["a"])(ce,i,r,!1,null,"750a726a",null);t["default"]=ge.exports;F()(ge,"components",{QLayout:oe["a"],QAjaxBar:le["a"],QPageContainer:de["a"],QHeader:he["a"],QToolbar:E["a"],QBtn:O["a"],QToolbarTitle:pe["a"],QInput:ue["a"],QIcon:j["a"],QSpace:H["a"],QBadge:me["a"],QTooltip:fe["a"],QToggle:z["a"],QDrawer:N["a"],QScrollArea:u["a"],QList:m["a"],QItem:g["a"],QItemSection:v["a"],QItemLabel:f["a"],QSeparator:L["a"],QField:U["a"]}),F()(ge,"directives",{Ripple:C["a"]})},1313:function(e,t,a){"use strict";var i=a("89ee"),r=a.n(i);r.a},"242e":function(e,t,a){},"600c":function(e,t,a){},"79d8":function(e,t,a){},"80d7":function(e,t,a){"use strict";var i=a("0c66"),r=a.n(i);r.a},"89ee":function(e,t,a){},9339:function(e,t,a){},d3bf:function(e,t,a){"use strict";var i=a("e52e"),r=a.n(i);r.a},d7ca:function(e,t,a){"use strict";var i=a("9339"),r=a.n(i);r.a},e23a:function(e,t,a){"use strict";var i=a("79d8"),r=a.n(i);r.a},e40c:function(e,t,a){"use strict";var i=a("600c"),r=a.n(i);r.a},e52e:function(e,t,a){},e9ef:function(e,t,a){"use strict";var i=a("242e"),r=a.n(i);r.a}}]);