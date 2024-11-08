const NavItem = ({item, onClick}) => {
    const{key,label,icon} = item;
    return (   
        <li >
            <button  className="menu-buttons" onClick={()=>onClick(key)}>
                <i className="icon">{icon}</i>
                {label}
            </button>
        </li>
    )
};
export default NavItem;