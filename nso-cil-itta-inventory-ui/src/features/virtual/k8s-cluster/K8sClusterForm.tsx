import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { K8S_CLUSTER_LIFECYCLE_STAGE_OPTIONS, K8sClusterLifecycleStage } from './types';
import { validateK8sCluster, cleanK8sClusterValues } from './schema';
import { K8SCLUSTER_DEFAULTS } from './types';
import type { K8sClusterFormValues, K8sClusterInput } from './types';

interface Props {
  initial?: K8sClusterFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: K8sClusterInput) => void;
  onCancel?: () => void;
}

export function K8sClusterForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<K8sClusterFormValues>({ ...K8SCLUSTER_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof K8sClusterFormValues>(key: K, value: K8sClusterFormValues[K]) {
    setValues((prev) => ({ ...prev, [key]: value }));
    if (errors[key as string]) {
      setErrors((prev) => {
        const next = { ...prev };
        delete next[key as string];
        return next;
      });
    }
  }

  function submit(ev: React.FormEvent) {
    ev.preventDefault();
    const result = validateK8sCluster(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanK8sClusterValues(values as Record<string, unknown>) as unknown as K8sClusterInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="K8s Cluster form">
      {serverError ? <div role="alert" className="form-error">{serverError}</div> : null}
      <FormField label="Code" htmlFor="code" error={errors.code} required={true}>
        <Input
          id="code"
          value={values.code ?? ''}
          maxLength={255}
          onChange={(v) => set('code', v)}
        />
      </FormField>
      <FormField label="Name" htmlFor="name" error={errors.name} required={true}>
        <Input
          id="name"
          value={values.name ?? ''}
          maxLength={255}
          onChange={(v) => set('name', v)}
        />
      </FormField>
      <FormField label="Description" htmlFor="description" error={errors.description} required={false}>
        <TextArea
          id="description"
          value={values.description ?? ''}
          maxLength={4000}
          rows={4}
          onChange={(v) => set('description', v)}
        />
      </FormField>
      <FormField label="Status" htmlFor="status" error={errors.status} required={true}>
        <Select
          id="status"
          value={values.status ?? ''}
          options={LIFECYCLE_OPTIONS}
          allowEmpty={false}
          onChange={(v) => set('status', (v || undefined) as LifecycleStatus | undefined)}
        />
      </FormField>
      <FormField label="External Ref" htmlFor="externalRef" error={errors.externalRef} required={false}>
        <Input
          id="externalRef"
          value={values.externalRef ?? ''}
          maxLength={255}
          onChange={(v) => set('externalRef', v)}
        />
      </FormField>
      <FormField label="Cluster Name" htmlFor="clusterName" error={errors.clusterName} required={true}>
        <Input
          id="clusterName"
          value={values.clusterName ?? ''}
          maxLength={255}
          onChange={(v) => set('clusterName', v)}
        />
      </FormField>
      <FormField label="Cluster Version" htmlFor="clusterVersion" error={errors.clusterVersion} required={true}>
        <Input
          id="clusterVersion"
          value={values.clusterVersion ?? ''}
          maxLength={255}
          onChange={(v) => set('clusterVersion', v)}
        />
      </FormField>
      <FormField label="Node Count" htmlFor="nodeCount" error={errors.nodeCount} required={true}>
        <NumberInput
          id="nodeCount"
          value={values.nodeCount}
          step={1}
          onChange={(v) => set('nodeCount', v)}
        />
      </FormField>
      <FormField label="Cloud Account Id" htmlFor="cloudAccountId" error={errors.cloudAccountId} required={false}>
        <Input
          id="cloudAccountId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.cloudAccountId ?? ''}
          onChange={(v) => set('cloudAccountId', v)}
        />
      </FormField>
      <FormField label="Region" htmlFor="region" error={errors.region} required={false}>
        <Input
          id="region"
          value={values.region ?? ''}
          maxLength={255}
          onChange={(v) => set('region', v)}
        />
      </FormField>
      <FormField label="Api Endpoint" htmlFor="apiEndpoint" error={errors.apiEndpoint} required={false}>
        <Input
          id="apiEndpoint"
          value={values.apiEndpoint ?? ''}
          maxLength={255}
          onChange={(v) => set('apiEndpoint', v)}
        />
      </FormField>
      <FormField label="Effective To" htmlFor="effectiveTo" error={errors.effectiveTo} required={false}>
        <DateInput
          id="effectiveTo"
          value={values.effectiveTo ?? ''}
          onChange={(v) => set('effectiveTo', v)}
        />
      </FormField>
      <FormField label="Labels" htmlFor="labels" error={errors.labels} required={false}>
        <Input
          id="labels"
          value={values.labels ?? ''}
          maxLength={255}
          onChange={(v) => set('labels', v)}
        />
      </FormField>
      <FormField label="Lifecycle Stage" htmlFor="lifecycleStage" error={errors.lifecycleStage} required={false}>
        <Select
          id="lifecycleStage"
          value={values.lifecycleStage ?? ''}
          options={K8S_CLUSTER_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as K8sClusterLifecycleStage | undefined)}
        />
      </FormField>
      <FormField label="Source System" htmlFor="sourceSystem" error={errors.sourceSystem} required={false}>
        <Input
          id="sourceSystem"
          value={values.sourceSystem ?? ''}
          maxLength={255}
          onChange={(v) => set('sourceSystem', v)}
        />
      </FormField>
      <FormField label="Owner" htmlFor="owner" error={errors.owner} required={false}>
        <Input
          id="owner"
          value={values.owner ?? ''}
          maxLength={255}
          onChange={(v) => set('owner', v)}
        />
      </FormField>
      <div className="entity-form__actions">
        {onCancel ? (
          <Button type="button" variant="ghost" onClick={onCancel}>
            Cancel
          </Button>
        ) : null}
        <Button type="submit" disabled={busy}>
          {busy ? 'Saving…' : submitLabel}
        </Button>
      </div>
    </form>
  );
}
