#
# rpmbuild   -bb dashboard.spec "--define=build_dir TOP_ECX_GIT_DIR" "--define=distribution_dir /tmp" 
#
%global __os_install_post %{nil}
%define debug_package %{nil}
%define microservice  ngxdd

%define service_name  dynamic-dashboard

%define microservice_jar %(basename $(ls -1 %{build_dir}/target/%{microservice}*.jar| head -n1) )

%define _unpackaged_files_terminate_build 0

Name:	        dashboard	
Version:	%{version}
Release:	%{build_number}
Summary:	NGX Dynamic Dashboard Framework	

Group:	Applications	
License: Catalogic Software 	
URL:www.catalogicsoftware.com		

BuildRoot:	%{_tmppath}/%{name}-%{version}-%{release}-root-%(%{__id_u} -n)

Prefix: /opt/SPP
BuildArch: noarch 


%description
NGX Dynamic Dashboard Framework
https://github.com/catalogicsoftware/ngx-dynamic-dashboard-framework


%prep

%build

%install


mkdir -p ${RPM_BUILD_ROOT}/%{prefix}/lib
mkdir -p ${RPM_BUILD_ROOT}/lib/systemd/system/

cp -f %{build_dir}/target/%{microservice_jar} ${RPM_BUILD_ROOT}/%{prefix}/lib/%{microservice}.jar
cp -f %{build_dir}/systemd/dashboard.service ${RPM_BUILD_ROOT}/lib/systemd/system/%{service_name}.service

%clean

mkdir -p %{distribution_dir}
cp -p %{_topdir}/RPMS/%{buildarch}/%{name}-%{version}-%{release}.* %{distribution_dir}/
 [ ${RPM_BUILD_ROOT} != "/" ] && rm -rf ${RPM_BUILD_ROOT}



%files
%defattr(-,root,root,-)
%{prefix}/lib/%{microservice}.jar
/lib/systemd/system/%{service_name}.service

%doc

%post

/usr/bin/systemctl enable %{service_name}

%pre


%preun
if test $1 = 0
then
 /usr/bin/systemctl disable %{service_name}
fi

%postun
if test $1 -ge 1
then
 /usr/bin/systemctl restart %{service_name} >/dev/null 2>&1 || :
fi




%changelog
