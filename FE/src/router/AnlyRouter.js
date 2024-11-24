import {lazy, Suspense} from "react";
import {Navigate} from "react-router-dom";

const Loading = <div>Loading....</div>
const BoardList = lazy(()=> import("../pages/board/ListPage"))
const BoardRead = lazy( ()=> import("../pages/board/ReadPage"))
const BoardModify = lazy(() => import("../pages/board/ModifyPage"))


const AnlyRouter = () => {
    return [
        {
            path : 'list',
            element : <Suspense fallback={Loading}><BoardList/></Suspense>
        },
        {
            path:'',
            element: <Navigate replace={true} to={'list'} />
        },
        {
            path:'read/:tno',
            element:<Suspense fallback={Loading}><BoardRead/></Suspense>
        },
        {
            path: "modify/:tno",
            element: <Suspense fallback={Loading}><BoardModify/></Suspense>
        }
    ]
}
export  default  AnlyRouter