import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { VRF_INSTANCE_LIFECYCLE_STAGE_OPTIONS, VrfInstanceLifecycleStage } from './types';
import { validateVrfInstance, cleanVrfInstanceValues } from './schema';
import { VRFINSTANCE_DEFAULTS } from './types';
import type { VrfInstanceFormValues, VrfInstanceInput } from './types';

interface Props {
  initial?: VrfInstanceFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: VrfInstanceInput) => void;
  onCancel?: () => void;
}

export function VrfInstanceForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<VrfInstanceFormValues>({ ...VRFINSTANCE_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof VrfInstanceFormValues>(key: K, value: VrfInstanceFormValues[K]) {
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
    const result = validateVrfInstance(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanVrfInstanceValues(values as Record<string, unknown>) as unknown as VrfInstanceInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Vrf Instance form">
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
      <FormField label="Vrf Name" htmlFor="vrfName" error={errors.vrfName} required={true}>
        <Input
          id="vrfName"
          value={values.vrfName ?? ''}
          maxLength={255}
          onChange={(v) => set('vrfName', v)}
        />
      </FormField>
      <FormField label="Route Distinguisher" htmlFor="routeDistinguisher" error={errors.routeDistinguisher} required={true}>
        <Input
          id="routeDistinguisher"
          value={values.routeDistinguisher ?? ''}
          maxLength={255}
          onChange={(v) => set('routeDistinguisher', v)}
        />
      </FormField>
      <FormField label="Import Targets" htmlFor="importTargets" error={errors.importTargets} required={false}>
        <Input
          id="importTargets"
          value={values.importTargets ?? ''}
          maxLength={255}
          onChange={(v) => set('importTargets', v)}
        />
      </FormField>
      <FormField label="Export Targets" htmlFor="exportTargets" error={errors.exportTargets} required={false}>
        <Input
          id="exportTargets"
          value={values.exportTargets ?? ''}
          maxLength={255}
          onChange={(v) => set('exportTargets', v)}
        />
      </FormField>
      <FormField label="Device Id" htmlFor="deviceId" error={errors.deviceId} required={false}>
        <Input
          id="deviceId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.deviceId ?? ''}
          onChange={(v) => set('deviceId', v)}
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
          options={VRF_INSTANCE_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as VrfInstanceLifecycleStage | undefined)}
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
