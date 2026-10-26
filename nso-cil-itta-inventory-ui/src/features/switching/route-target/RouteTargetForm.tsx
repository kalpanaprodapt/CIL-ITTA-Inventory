import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { ROUTE_TARGET_RT_TYPE_OPTIONS, ROUTE_TARGET_LIFECYCLE_STAGE_OPTIONS, ROUTE_TARGET_CRITICALITY_OPTIONS, RouteTargetRtType, RouteTargetLifecycleStage, RouteTargetCriticality } from './types';
import { validateRouteTarget, cleanRouteTargetValues } from './schema';
import { ROUTETARGET_DEFAULTS } from './types';
import type { RouteTargetFormValues, RouteTargetInput } from './types';

interface Props {
  initial?: RouteTargetFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: RouteTargetInput) => void;
  onCancel?: () => void;
}

export function RouteTargetForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<RouteTargetFormValues>({ ...ROUTETARGET_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof RouteTargetFormValues>(key: K, value: RouteTargetFormValues[K]) {
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
    const result = validateRouteTarget(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanRouteTargetValues(values as Record<string, unknown>) as unknown as RouteTargetInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Route Target form">
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
      <FormField label="Asn" htmlFor="asn" error={errors.asn} required={true}>
        <NumberInput
          id="asn"
          value={values.asn}
          step={1}
          onChange={(v) => set('asn', v)}
        />
      </FormField>
      <FormField label="Assigned Number" htmlFor="assignedNumber" error={errors.assignedNumber} required={true}>
        <NumberInput
          id="assignedNumber"
          value={values.assignedNumber}
          step={1}
          onChange={(v) => set('assignedNumber', v)}
        />
      </FormField>
      <FormField label="Rt Type" htmlFor="rtType" error={errors.rtType} required={false}>
        <Select
          id="rtType"
          value={values.rtType ?? ''}
          options={ROUTE_TARGET_RT_TYPE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('rtType', (v || undefined) as RouteTargetRtType | undefined)}
        />
      </FormField>
      <FormField label="Vrf Id" htmlFor="vrfId" error={errors.vrfId} required={false}>
        <Input
          id="vrfId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.vrfId ?? ''}
          onChange={(v) => set('vrfId', v)}
        />
      </FormField>
      <FormField label="Lifecycle Stage" htmlFor="lifecycleStage" error={errors.lifecycleStage} required={false}>
        <Select
          id="lifecycleStage"
          value={values.lifecycleStage ?? ''}
          options={ROUTE_TARGET_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as RouteTargetLifecycleStage | undefined)}
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
          options={ROUTE_TARGET_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as RouteTargetCriticality | undefined)}
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
