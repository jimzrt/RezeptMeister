(function(e){function r(r){for(var n,o,c=r[0],u=r[1],l=r[2],s=0,p=[];s<c.length;s++)o=c[s],Object.prototype.hasOwnProperty.call(a,o)&&a[o]&&p.push(a[o][0]),a[o]=0;for(n in u)Object.prototype.hasOwnProperty.call(u,n)&&(e[n]=u[n]);f&&f(r);while(p.length)p.shift()();return i.push.apply(i,l||[]),t()}function t(){for(var e,r=0;r<i.length;r++){for(var t=i[r],n=!0,o=1;o<t.length;o++){var c=t[o];0!==a[c]&&(n=!1)}n&&(i.splice(r--,1),e=u(u.s=t[0]))}return e}var n={},o={1:0},a={1:0},i=[];function c(e){return u.p+"js/"+({}[e]||e)+"."+{2:"fa00e8fe",3:"bba3ca01",4:"01275532",5:"2eb067a7"}[e]+".js"}function u(r){if(n[r])return n[r].exports;var t=n[r]={i:r,l:!1,exports:{}};return e[r].call(t.exports,t,t.exports,u),t.l=!0,t.exports}u.e=function(e){var r=[],t={2:1,3:1,4:1};o[e]?r.push(o[e]):0!==o[e]&&t[e]&&r.push(o[e]=new Promise((function(r,t){for(var n="css/"+({}[e]||e)+"."+{2:"15d261a4",3:"37680e01",4:"13c36ecb",5:"31d6cfe0"}[e]+".css",a=u.p+n,i=document.getElementsByTagName("link"),c=0;c<i.length;c++){var l=i[c],s=l.getAttribute("data-href")||l.getAttribute("href");if("stylesheet"===l.rel&&(s===n||s===a))return r()}var p=document.getElementsByTagName("style");for(c=0;c<p.length;c++){l=p[c],s=l.getAttribute("data-href");if(s===n||s===a)return r()}var f=document.createElement("link");f.rel="stylesheet",f.type="text/css",f.onload=r,f.onerror=function(r){var n=r&&r.target&&r.target.src||a,i=new Error("Loading CSS chunk "+e+" failed.\n("+n+")");i.code="CSS_CHUNK_LOAD_FAILED",i.request=n,delete o[e],f.parentNode.removeChild(f),t(i)},f.href=a;var d=document.getElementsByTagName("head")[0];d.appendChild(f)})).then((function(){o[e]=0})));var n=a[e];if(0!==n)if(n)r.push(n[2]);else{var i=new Promise((function(r,t){n=a[e]=[r,t]}));r.push(n[2]=i);var l,s=document.createElement("script");s.charset="utf-8",s.timeout=120,u.nc&&s.setAttribute("nonce",u.nc),s.src=c(e);var p=new Error;l=function(r){s.onerror=s.onload=null,clearTimeout(f);var t=a[e];if(0!==t){if(t){var n=r&&("load"===r.type?"missing":r.type),o=r&&r.target&&r.target.src;p.message="Loading chunk "+e+" failed.\n("+n+": "+o+")",p.name="ChunkLoadError",p.type=n,p.request=o,t[1](p)}a[e]=void 0}};var f=setTimeout((function(){l({type:"timeout",target:s})}),12e4);s.onerror=s.onload=l,document.head.appendChild(s)}return Promise.all(r)},u.m=e,u.c=n,u.d=function(e,r,t){u.o(e,r)||Object.defineProperty(e,r,{enumerable:!0,get:t})},u.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},u.t=function(e,r){if(1&r&&(e=u(e)),8&r)return e;if(4&r&&"object"===typeof e&&e&&e.__esModule)return e;var t=Object.create(null);if(u.r(t),Object.defineProperty(t,"default",{enumerable:!0,value:e}),2&r&&"string"!=typeof e)for(var n in e)u.d(t,n,function(r){return e[r]}.bind(null,n));return t},u.n=function(e){var r=e&&e.__esModule?function(){return e["default"]}:function(){return e};return u.d(r,"a",r),r},u.o=function(e,r){return Object.prototype.hasOwnProperty.call(e,r)},u.p="",u.oe=function(e){throw console.error(e),e};var l=window["webpackJsonp"]=window["webpackJsonp"]||[],s=l.push.bind(l);l.push=r,l=l.slice();for(var p=0;p<l.length;p++)r(l[p]);var f=s;i.push([0,0]),t()})({0:function(e,r,t){e.exports=t("2f39")},"0047":function(e,r,t){},"2f39":function(e,r,t){"use strict";t.r(r);t("e6cf"),t("5319"),t("7d6e"),t("e54f"),t("985d"),t("0047"),t("71da"),t("2f80");var n=t("2b0e"),o=t("1f91"),a=t("42d2"),i=t("b05d"),c=t("2a19"),u=t("f508"),l=t("436b"),s=t("18d6");n["a"].use(i["a"],{config:{dark:!0},lang:o["a"],iconSet:a["a"],plugins:{Notify:c["a"],Loading:u["a"],Dialog:l["a"],LocalStorage:s["a"]}});var p=function(){var e=this,r=e.$createElement,t=e._self._c||r;return t("div",{attrs:{id:"q-app"}},[t("router-view")],1)},f=[],d={name:"App"},g=d,h=t("2877"),y=Object(h["a"])(g,p,f,!1,null,null,null),b=y.exports,m=t("2f62");n["a"].use(m["a"]);var v=function(){const e=new m["a"].Store({modules:{},strict:!1});return e},w=t("8c4f");const k=[{path:"/",component:()=>Promise.all([t.e(0),t.e(4)]).then(t.bind(null,"713b")),children:[{path:"",component:()=>Promise.all([t.e(0),t.e(2)]).then(t.bind(null,"0d77"))},{path:"/rezept/:recipeSeoTitle",name:"recipe",component:()=>Promise.all([t.e(0),t.e(3)]).then(t.bind(null,"78b5")),props:!0}]},{path:"*",component:()=>t.e(5).then(t.bind(null,"e51e"))}];var P=k;n["a"].use(w["a"]);var O=function(){const e=new w["a"]({scrollBehavior(e,r,t){return"scrollRestoration"in history&&(history.scrollRestoration="manual"),t||{x:0,y:0}},routes:P,mode:"hash",base:""});return e},j=async function(){const e="function"===typeof v?await v({Vue:n["a"]}):v,r="function"===typeof O?await O({Vue:n["a"],store:e}):O;e.$router=r;const t={router:r,store:e,render:e=>e(b),el:"#q-app"};return{app:t,store:e,router:r}},S=t("bc3a"),x=t.n(S),_=t("691a");n["a"].prototype.$axios=x.a.create({baseURL:"",adapter:Object(_["a"])(x.a.defaults.adapter)});var E=t("5b5c");const C="";async function L(){const{app:e,store:r,router:t}=await j();let o=!1;const a=e=>{o=!0;const r=Object(e)===e?t.resolve(e).route.fullPath:e;window.location.href=r},i=window.location.href.replace(window.location.origin,""),c=[void 0,E["a"]];for(let l=0;!1===o&&l<c.length;l++)if("function"===typeof c[l])try{await c[l]({app:e,router:t,store:r,Vue:n["a"],ssrContext:null,redirect:a,urlPath:i,publicPath:C})}catch(u){return u&&u.url?void(window.location.href=u.url):void console.error("[Quasar] boot error:",u)}!0!==o&&new n["a"](e)}L()},eba0:function(e,r,t){"use strict";t.d(r,"a",(function(){return n}));const n=[{name:"quasarish",isDark:!1,colors:{primary:"blue-grey-6",secondary:"brown-4",accent:"grey-7",info:"grey-5",warning:"amber-6",positive:"green-14",negative:"red-10",background:"grey-2"}},{name:"sunset",isDark:!1,colors:{primary:"deep-purple-9",secondary:"orange-10",accent:"light-green-7",info:"cyan-9",warning:"yellow-9",positive:"green-9",negative:"red-10",background:"grey-1"}},{name:"pastels",isDark:!0,colors:{primary:"blue-grey-9",secondary:"brown-14",accent:"grey-13",info:"grey-10",warning:"orange-3",positive:"green-13",negative:"red-3",background:"grey-10"}},{name:"ocean",isDark:!0,colors:{primary:"green-2",secondary:"teal-2",accent:"purple-2",info:"blue-3",warning:"yellow-3",positive:"light-green-3",negative:"orange-3",background:"grey-10"}}]}});