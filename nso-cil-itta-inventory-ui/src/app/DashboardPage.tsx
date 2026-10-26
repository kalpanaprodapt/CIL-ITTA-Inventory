import { Link } from 'react-router-dom';
import { NAVIGATION } from '@/app/navigation';
import { PageHeader } from '@/shared/components/page';

export function DashboardPage() {
  const total = NAVIGATION.reduce((n, d) => n + d.entities.length, 0);
  return (
    <section>
      <PageHeader title="Network inventory" subtitle={`${NAVIGATION.length} domains · ${total} record types`} />
      <div className="dashboard-grid">
        {NAVIGATION.map((d) => (
          <article key={d.domain} className="card">
            <h2>{d.title}</h2>
            <ul>
              {d.entities.map((e) => (
                <li key={e.name}>
                  <Link to={`/${d.domain}/${e.path}`}>{e.title}</Link>
                </li>
              ))}
            </ul>
          </article>
        ))}
      </div>
    </section>
  );
}
