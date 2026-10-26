import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { ErrorBanner, PageHeader, Spinner } from '@/shared/components/page';
import { errorMessage } from '@/shared/api/http';
import { MplsLspForm } from './MplsLspForm';
import { useCreateMplsLsp, useUpdateMplsLsp, useMplsLsp } from './hooks';
import type { MplsLspInput } from './types';

export function MplsLspCreatePage() {
  const navigate = useNavigate();
  const create = useCreateMplsLsp();
  const [error, setError] = useState<string>();

  function onSubmit(input: MplsLspInput) {
    setError(undefined);
    create.mutate(input, {
      onSuccess: (created) => navigate(`/mpls/mpls-lsps/${created.id}`),
      onError: (err) => setError(errorMessage(err)),
    });
  }

  return (
    <section aria-labelledby="mpls-lsp-new-title">
      <PageHeader id="mpls-lsp-new-title" title="New Mpls Lsp" />
      <MplsLspForm
        submitLabel="Create"
        busy={create.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate('/mpls/mpls-lsps')}
      />
    </section>
  );
}

export function MplsLspEditPage() {
  const { id = '' } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const query = useMplsLsp(id);
  const update = useUpdateMplsLsp(id);
  const [error, setError] = useState<string>();

  if (query.isLoading) {
    return <Spinner label="Loading Mpls Lsp" />;
  }
  if (query.isError || !query.data) {
    return <ErrorBanner message="Could not load this Mpls Lsp." onRetry={() => query.refetch()} />;
  }

  function onSubmit(input: MplsLspInput) {
    setError(undefined);
    update.mutate(
      { ...input, expectedVersion: query.data?.version },
      {
        onSuccess: () => navigate(`/mpls/mpls-lsps/${id}`),
        onError: (err) => setError(errorMessage(err)),
      },
    );
  }

  return (
    <section aria-labelledby="mpls-lsp-edit-title">
      <PageHeader id="mpls-lsp-edit-title" title={`Edit ${query.data.code}`} />
      <MplsLspForm
        initial={query.data}
        submitLabel="Save changes"
        busy={update.isPending}
        serverError={error}
        onSubmit={onSubmit}
        onCancel={() => navigate(`/mpls/mpls-lsps/${id}`)}
      />
    </section>
  );
}
