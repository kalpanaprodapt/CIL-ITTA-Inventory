import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { SiteForm } from './SiteForm';
import { useCreateSite, useUpdateSite, useSite } from './hooks';
import type { SiteInput } from './types';

export function SiteCreatePage() {
  const navigate = useNavigate();
  const create = useCreateSite();
  const [error, setError] = useState<string>();

  function onSubmit(input: SiteInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/geography/sites/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="site-new-title">
      <PageHeader id="site-new-title" title="New Site" />
      <SiteForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/geography/sites')}
      />
    </section>
  );
}

export function SiteEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useSite(id);
  const update = useUpdateSite(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Site" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Site." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: SiteInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/geography/sites/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="site-edit-title">
      <PageHeader id="site-edit-title" title={`Edit ${query.data.code}`} />
      <SiteForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/geography/sites/${id}`)}
      />
    </section>
  );
}
