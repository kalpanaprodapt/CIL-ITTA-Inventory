import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { SERVICE_INSTANCE_SERVICE_STATE_OPTIONS, SERVICE_INSTANCE_LIFECYCLE_STAGE_OPTIONS, SERVICE_INSTANCE_CRITICALITY_OPTIONS, ServiceInstanceServiceState, ServiceInstanceLifecycleStage, ServiceInstanceCriticality } from './types';
import { validateServiceInstance, cleanServiceInstanceValues } from './schema';
import { SERVICEINSTANCE_DEFAULTS } from './types';
import type { ServiceInstanceFormValues, ServiceInstanceInput } from './types';

interface Props {
  initial?: ServiceInstanceFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: ServiceInstanceInput) => void;
  onCancel?: () => void;
}

export function ServiceInstanceForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<ServiceInstanceFormValues>({ ...SERVICEINSTANCE_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof ServiceInstanceFormValues>(key: K, value: ServiceInstanceFormValues[K]) {
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
    const result = validateServiceInstance(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanServiceInstanceValues(values as Record<string, unknown>) as unknown as ServiceInstanceInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Service Instance form">
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
      <FormField label="Catalog Item Id" htmlFor="catalogItemId" error={errors.catalogItemId} required={true}>
        <Input
          id="catalogItemId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.catalogItemId ?? ''}
          onChange={(v) => set('catalogItemId', v)}
        />
      </FormField>
      <FormField label="Customer Id" htmlFor="customerId" error={errors.customerId} required={true}>
        <Input
          id="customerId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.customerId ?? ''}
          onChange={(v) => set('customerId', v)}
        />
      </FormField>
      <FormField label="Activated On" htmlFor="activatedOn" error={errors.activatedOn} required={false}>
        <DateInput
          id="activatedOn"
          value={values.activatedOn ?? ''}
          onChange={(v) => set('activatedOn', v)}
        />
      </FormField>
      <FormField label="Bandwidth Mbps" htmlFor="bandwidthMbps" error={errors.bandwidthMbps} required={false}>
        <NumberInput
          id="bandwidthMbps"
          value={values.bandwidthMbps}
          step={1}
          onChange={(v) => set('bandwidthMbps', v)}
        />
      </FormField>
      <FormField label="Service State" htmlFor="serviceState" error={errors.serviceState} required={false}>
        <Select
          id="serviceState"
          value={values.serviceState ?? ''}
          options={SERVICE_INSTANCE_SERVICE_STATE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('serviceState', (v || undefined) as ServiceInstanceServiceState | undefined)}
        />
      </FormField>
      <FormField label="Lifecycle Stage" htmlFor="lifecycleStage" error={errors.lifecycleStage} required={false}>
        <Select
          id="lifecycleStage"
          value={values.lifecycleStage ?? ''}
          options={SERVICE_INSTANCE_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as ServiceInstanceLifecycleStage | undefined)}
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
      <FormField label="Region" htmlFor="region" error={errors.region} required={false}>
        <Input
          id="region"
          value={values.region ?? ''}
          maxLength={255}
          onChange={(v) => set('region', v)}
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
      <FormField label="Criticality" htmlFor="criticality" error={errors.criticality} required={false}>
        <Select
          id="criticality"
          value={values.criticality ?? ''}
          options={SERVICE_INSTANCE_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as ServiceInstanceCriticality | undefined)}
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
